package com.lcl.easyexcel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.lcl.easyexcel.db.SimulationDb;
import com.lcl.easyexcel.excel.listener.FirstSheetListener;
import com.lcl.easyexcel.excel.listener.SecondSheetListener;
import com.lcl.easyexcel.exception.ExcelException;
import com.lcl.easyexcel.executor.ThreadPoolManager;
import com.lcl.easyexcel.pojo.FirstSheet;
import com.lcl.easyexcel.pojo.SecondSheet;
import com.lcl.easyexcel.service.EasyExcelService;
import com.lcl.easyexcel.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.impl.internal.util.ThreadFactoryUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @ClassName: EasyExcelServiceImpl
 * @Description: 实现类
 * @Author: Chunliang.Li
 * @Date: 2020/6/29 17:33
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Service
@Slf4j
public class EasyExcelServiceImpl implements EasyExcelService {

    private static ThreadLocal<Boolean> threadLocal = ThreadLocal.withInitial(() -> false);
    @Resource
    private ThreadPoolExecutor easyrExcelThreadPool;

    @Override
    public void ep(HttpServletResponse response, HttpServletRequest request, String filename) throws InterruptedException, ExecutionException, TimeoutException, IOException {
        this.setResponse(response, request, filename);
        ServletOutputStream respOutPutStream = null;
        try {
            respOutPutStream = response.getOutputStream();
            //多线程调度并行执行SQL
            Future<List<FirstSheet>> firstData = easyrExcelThreadPool.submit(() -> {
                List<FirstSheet> list = SimulationDb.selectFirst();
                log.info("{}-{}->数据准备完毕", Thread.currentThread().getName(), Thread.currentThread().getId());
                return list;
            });
            Future<List<SecondSheet>> secondData = easyrExcelThreadPool.submit(() -> {
                List<SecondSheet> list = SimulationDb.selectSecond();
                log.info("{}-{}->数据准备完毕", Thread.currentThread().getName(), Thread.currentThread().getId());
                return list;
            });
            //构建ExcelWriter
            ExcelWriter ew = EasyExcel.write(respOutPutStream).build();
            //写出sheet对应sheet内容
            ew.write(firstData.get(10, TimeUnit.SECONDS), EasyExcel.writerSheet(0, "first").head(FirstSheet.class).build());
            ew.write(secondData.get(10, TimeUnit.SECONDS), EasyExcel.writerSheet(1, "second").head(SecondSheet.class).build());
            ew.finish();
        } finally {
            if (respOutPutStream != null) {
                try {
                    respOutPutStream.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
    }

    @Override
    public void im(MultipartFile[] files) throws IOException {
        //标志位，是否需要回滚
        AtomicBoolean isRollback = new AtomicBoolean(false);
        //保留添加的id做回滚操作
        ConcurrentHashMap<String, List<String>> map = new ConcurrentHashMap<>(16);
        for (MultipartFile file : files) {
            ExcelReader reader = null;
            try {
                reader = EasyExcel.read(file.getInputStream()).build();
                //为每个sheet注册对应的监听器(每个sheet读取方式处理逻辑均不同)
                ReadSheet firstsheet = EasyExcel.readSheet(0).head(FirstSheet.class).registerReadListener(new FirstSheetListener(isRollback, map, easyrExcelThreadPool)).build();
                ReadSheet secondsheet = EasyExcel.readSheet(1).head(SecondSheet.class).registerReadListener(new SecondSheetListener(isRollback, map, easyrExcelThreadPool)).build();
                //读取
                reader.read(firstsheet, secondsheet);
            } finally {
                if (reader != null) {
                    //流关
                    reader.finish();
                }
            }
        }
        //异常回滚
        if (isRollback.get()) {
            SimulationDb.deleteFirst(map.get(Constant.FIRST_DB));
            SimulationDb.deleteSecond(map.get(Constant.SECOND_DB));
            throw new ExcelException("导入失败");
        }
    }

    /**
     * 设置响应参数
     */
    private void setResponse(HttpServletResponse response, HttpServletRequest request, String filename) {
        response.setHeader("Content-Disposition", "attachment;filename=" + encodeFilename(request, filename) + ".xlsx");
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
    }

    /**
     * 处理导出名称乱码问题
     */
    private String encodeFilename(HttpServletRequest request, String name) {
        try {
            //火狐浏览器导出名称乱码问题
            if (request.getHeader("User-Agent").toLowerCase().contains("firefox")) {
                return new String(name.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }
            return URLEncoder.encode(name, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

}

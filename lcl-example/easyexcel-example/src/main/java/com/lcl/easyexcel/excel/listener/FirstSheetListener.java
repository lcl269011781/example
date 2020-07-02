package com.lcl.easyexcel.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ConverterUtils;
import com.alibaba.fastjson.JSONObject;
import com.lcl.easyexcel.db.SimulationDb;
import com.lcl.easyexcel.executor.ThreadPoolManager;
import com.lcl.easyexcel.pojo.FirstSheet;
import com.lcl.easyexcel.util.Constant;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @ClassName: FirstSheetListener
 * @Description: 读取第一个firstsheet监听器
 * @Author: Chunliang.Li
 * @Date: 2020/6/30 8:41
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Slf4j
public class FirstSheetListener<T> implements ReadListener<T> {

    private List<FirstSheet> list = new ArrayList<>();
    private AtomicBoolean isRollback;
    private ConcurrentHashMap<String, List<String>> map;
    private int total;
    private ThreadPoolExecutor execute;

    public FirstSheetListener(AtomicBoolean isRollback, ConcurrentHashMap<String, List<String>> map,ThreadPoolExecutor execute) {
        this.isRollback = isRollback;
        this.execute = execute;
        this.map = map;
        List<String> ids = new ArrayList<>();
        map.put(Constant.FIRST_DB, ids);
    }

    @Override
    public void onException(Exception e, AnalysisContext analysisContext) throws Exception {
        isRollback.set(true);
    }

    @Override
    public void invoke(T o, AnalysisContext analysisContext) {
        //在这里处理读取的内容
        FirstSheet content = JSONObject.parseObject(JSONObject.toJSONString(o), FirstSheet.class);
        String id = UUID.randomUUID().toString().replace("-", "");
        content.setId(id);
        log.info("--->>>读取到的内容为:{}", content);
        //可设置一下批量读取后批量添加,例子设置2
        list.add(content);
        map.get(Constant.FIRST_DB).add(id);
        if (list.size() >= 2 || analysisContext.readRowHolder().getRowIndex() == total) {
            List<FirstSheet> result = new ArrayList<>(list);
            //多线程事务自动提交
            execute.execute(() -> {
                try {
                    SimulationDb.batchInsertFirst(result);
                    //throw new Exception();
                } catch (Exception e) {
                    log.error("", e);
                    //发生异常等等乱七八糟的条件需要回滚事务。
                    this.isRollback.set(true);
                }
            });
            //置空
            list = new ArrayList<>();
        }
    }

    @Override
    public void extra(CellExtra cellExtra, AnalysisContext analysisContext) {
        log.info("=====");
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("读取完毕调用");
    }

    /**
     * 如果有下一行 继续读， 返回必须为true
     *
     * @param analysisContext
     * @return
     */
    @Override
    public boolean hasNext(AnalysisContext analysisContext) {
        return true;
    }

    @Override
    public void invokeHead(Map<Integer, CellData> map, AnalysisContext analysisContext) {
        Map<Integer, String> headMap = ConverterUtils.convertToStringMap(map, analysisContext);
        //可添加表头校验
        log.info("--->>>Firstsheet,header");
        total = analysisContext.readSheetHolder().getApproximateTotalRowNumber();
    }
}

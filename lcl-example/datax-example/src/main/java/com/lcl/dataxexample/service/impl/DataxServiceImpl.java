package com.lcl.dataxexample.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lcl.dataxexample.datax.DataxStart;
import com.lcl.dataxexample.datax.pojo.DataxJson;
import com.lcl.dataxexample.datax.service.DataxTemplateService;
import com.lcl.dataxexample.service.DataxService;
import com.lcl.dataxexample.util.NIOFileUtil;
import com.lcl.dataxexample.util.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName: DataxServiceImpl
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/2 10:16
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Service
@Slf4j
public class DataxServiceImpl implements DataxService {
    @Autowired
    private DataxTemplateService dataxTemplateService;
    @Resource
    private ThreadPoolExecutor dataxThreadPool;

    @Override
    public void exec(String id) {

        dataxThreadPool.execute(() -> {
            process(id);
        });

    }

    private void process(String id) {
        //1.数据库插入任务执行开始状态
        log.info("===>>> 插入任务执行开始状态");
        //2.生成datax支持的job.json文件
        String jobPath = generaterJsobFile(id);
        if (jobPath == null) {
            log.error("===>>> job.json生成失败");
            log.info("===>>> 插入任务执行结束状态---fail");
            return;
        }
        //3.启动任务
        log.info("===>>> 准备启动任务");
        try {
            DataxStart.start(jobPath, getTaskJvmParam(id));
            //4.更新任务状态
            log.info("===>>> 插入任务执行结束状态---success");
        } catch (Throwable throwable) {
            log.error("", throwable);
            log.error("===>>> 任务执行失败");
            log.info("===>>> 插入任务执行结束状态---fail");
        } finally {
            //5.删除临时文件
            removeJobFile(jobPath);
            //若有redis or kafka订阅任务执行状态，可进行发布。
            log.info("===>>> 任务状态已发布");
        }

    }

    private String generaterJsobFile(String id) {
        log.info("===>>> 准备生成job.json文件");
        DataxJson json = dataxTemplateService.generateDataxJobJson();
        String jobPath = PathUtil.getCurrentClasspath() + "/datax/job/" + id + ".json";
        File file = new File(jobPath);
        String jobJson = JSONObject.toJSONString(json);
        try {
            log.info("===> json:{}", jobJson);
            NIOFileUtil.write(file, jobJson);
        } catch (IOException e) {
            log.error("", e);
            log.info("===>>> 生成job.json文件失败");
            return null;
        }
        log.info("===>>> 生成job.json文件完毕");
        return jobPath;
    }

    private String getTaskJvmParam(String id) {
        //查询该条任务的jvm配置
        //假装查出来了
        return "-Xms100M -Xmx200M";
    }

    private void removeJobFile(String jobPath){
        try {
            FileUtils.forceDelete(new File(jobPath));
        } catch (IOException e) {
            log.error("",e);
            //临时文件删除失败，记录信息后续主动清理
        }
        log.info("===>>> {}临时文件删除",jobPath);
    }

}

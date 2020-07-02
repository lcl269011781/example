package com.lcl.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @ClassName: TestQuartzJob
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/1 14:40
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@DisallowConcurrentExecution
@Slf4j
public class FirstQuartzJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String jobName = jobExecutionContext.getJobDetail().getJobDataMap().getString("jobName");
        log.info("类FirstQuartzJob，{}定时启动了--->>>",jobName);
    }
}

package com.lcl.quartz.service.impl;

import com.lcl.quartz.manager.QuartzManager;
import com.lcl.quartz.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: JobServiceImpl
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/1 15:19
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Service
@Slf4j
public class JobServiceImpl implements JobService {
    @Autowired
    private QuartzManager quartzManager;

    @Override
    public void addJobForJobId(String jobId, Class jobClass, String cron) {
        try {
            quartzManager.addJob(jobId, jobClass, cron);
            log.info("{} 任务添加成功,执行周期为: 【{}】", jobId, cron);
        } catch (SchedulerException e) {
            log.error("{} 任务添加失败,失败原因: {}", jobId, e.getMessage());
        }
    }

    @Override
    public void updateJobCronForJobId(String jobId, String nowCron) {
        try {
            quartzManager.modifyJobTime(jobId, nowCron);
            log.info("{} 任务执行周期修改成功,修改后的执行周期为: 【{}】", jobId, nowCron);
        } catch (SchedulerException e) {
            log.error("{} 任务执行周期修改失败,失败原因: {}", jobId, e.getMessage());
        }
    }

    @Override
    public void deleteJobForJobId(String jobId) {
        try {
            quartzManager.removeJob(jobId);
            log.info("{} 任务移除成功", jobId);
        } catch (SchedulerException e) {
            log.error("{} 任务移除失败,失败原因: {}", jobId, e.getMessage());
        }
    }

    @Override
    public void pauseJobForJobId(String jobId) {
        try {
            quartzManager.pauseJob(jobId);
            log.info("{} 任务暂停成功", jobId);
        } catch (SchedulerException e) {
            log.error("{} 任务暂停失败,失败原因: {}", jobId, e.getMessage());
        }
    }

    @Override
    public void resumeJobForJobId(String jobId) {
        try {
            quartzManager.resumeJob(jobId);
            log.info("{} 任务恢复成功", jobId);
        } catch (SchedulerException e) {
            log.error("{} 任务恢复失败,失败原因: {}", jobId, e.getMessage());
        }
    }

    @Override
    public void startJobForJobId(String jobId) {
        try {
            quartzManager.startJob(jobId);
            log.info("{} 任务启动成功", jobId);
        } catch (SchedulerException e) {
            log.error("{} 任务启动失败,失败原因: {}", jobId, e.getMessage());
        }
    }
}

package com.lcl.quartz.manager;

import com.lcl.quartz.config.QuartzJobFactory;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: QuartzManager
 * @Description: job操作
 * @Author: Chunliang.Li
 * @Date: 2020/7/1 14:45
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Service
@Slf4j
public class QuartzManager {
    @Autowired
    private QuartzJobFactory quartzJobFactory;
    @Autowired
    private Scheduler scheduler;

    /**
     * 添加任务
     *
     * @param jobName  任务名
     * @param jobClass 任务执行的类
     * @param cron
     * @throws SchedulerException
     */
    public void addJob(String jobName, Class jobClass, String cron) throws SchedulerException {
        scheduler.setJobFactory(quartzJobFactory);
        //任务名，任务组，任务执行类
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobName, jobName)
                .withDescription("Quartz-Job")
                .build();
        setJobInfo(jobName, jobDetail);
        //触发器时间设定
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
                .cronSchedule(cron)
                //设置任务不过期
                .withMisfireHandlingInstructionDoNothing();
        //创建触发器
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, jobName)
                .withSchedule(cronScheduleBuilder)
                .build();
        //调度容器设置触发器对象和jobdetail
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }

    private void setJobInfo(String jobName, JobDetail jobDetail) {
        jobDetail.getJobDataMap().put("jobName", jobName);
    }

    /**
     * 修改一个任务的触发时间
     *
     * @param jobName
     * @param cron
     * @throws SchedulerException
     */
    public void modifyJobTime(String jobName, String cron) throws SchedulerException {
        scheduler.setJobFactory(quartzJobFactory);
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobName);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (trigger == null) {
            return;
        }
        String oldCron = trigger.getCronExpression();
        if (!oldCron.equalsIgnoreCase(cron)) {
            //触发器
            TriggerBuilder<Trigger> newTrigger = TriggerBuilder.newTrigger();
            //触发器名，触发器组
            newTrigger.withIdentity(jobName, jobName);
            newTrigger.startNow();
            //触发器时间设定
            newTrigger.withSchedule(CronScheduleBuilder.cronSchedule(cron).withMisfireHandlingInstructionDoNothing());
            //创建trigger对象
            trigger = (CronTrigger) newTrigger.build();
            //修改一个任务的触发时间
            scheduler.rescheduleJob(triggerKey, trigger);
        }

    }

    /**
     * 删除一个任务
     *
     * @param jobName
     * @throws SchedulerException
     */
    public void removeJob(String jobName) throws SchedulerException {
        scheduler.setJobFactory(quartzJobFactory);
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobName);
        //停止触发器
        scheduler.pauseTrigger(triggerKey);
        //移除触发器
        scheduler.unscheduleJob(triggerKey);
        //删除任务
        scheduler.deleteJob(JobKey.jobKey(jobName, jobName));
    }

    /**
     * 暂停任务
     * @param jobName
     * @throws SchedulerException
     */
    public void pauseJob(String jobName) throws SchedulerException {
        scheduler.setJobFactory(quartzJobFactory);
        scheduler.pauseJob(JobKey.jobKey(jobName,jobName));
    }

    /**
     * 恢复任务
     * @param jobName
     * @throws SchedulerException
     */
    public void resumeJob(String jobName) throws SchedulerException {
        scheduler.setJobFactory(quartzJobFactory);
        scheduler.resumeJob(JobKey.jobKey(jobName,jobName));
    }

    /**
     * 立即执行某个任务
     *
     * @param jobName
     * @throws SchedulerException
     */
    public void startJob(String jobName) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobName);
        scheduler.triggerJob(jobKey);
        scheduler.start();
    }

    /**
     * 计算某个cron 最近应该执行的时间段
     *
     * @param cron
     * @return
     * @throws ParseException
     */
    public List<Date> getRecentTriggerTime(String cron) throws ParseException {
        CronTriggerImpl cronTrigger = new CronTriggerImpl();
        cronTrigger.setCronExpression(cron);
        //格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //计算初始时间默认为2020年一月一
        Date parse = simpleDateFormat.parse("2020-01-01 00:00:00");
        //求初始时间到最近应该执行的时间
        return TriggerUtils.computeFireTimesBetween(cronTrigger, null, parse, new Date());
    }

    /**
     * 判断某个任务是否存在
     * @param jobName
     * @return
     */
    public boolean existJob(String jobName) {
        JobDetail jobDetail = null;
        try {
            jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobName));
            if (jobDetail == null) {
                return false;
            }
        } catch (SchedulerException e) {
            log.error("", e);
            return false;
        }
        return true;
    }

}

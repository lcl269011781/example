package com.lcl.quartz.service;

public interface JobService {
    /**
     * 持久化添加任务到Quartz管理
     *
     * @param jobId
     * @param jobClass
     * @param cron
     */
    void addJobForJobId(String jobId, Class jobClass, String cron);

    /**
     * 修改quartz管理的任务执行周期
     * @param jobId
     * @param nowCron
     */
    void updateJobCronForJobId(String jobId, String nowCron);

    /**
     * 从Quartz管理中持久化删除任务
     * @param jobId
     */
    void deleteJobForJobId(String jobId);

    /**
     * 暂停某个任务
     * @param jobId
     */
    void pauseJobForJobId(String jobId);

    /**
     * 从Quartz管理中恢复某任务
     * @param jobId
     */
    void resumeJobForJobId(String jobId);

    /**
     * 立即启动某个任务
     * @param jobId
     */
    void startJobForJobId(String jobId);


}

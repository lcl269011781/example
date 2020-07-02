package com.lcl.quartz.controller;

import com.lcl.quartz.job.FirstQuartzJob;
import com.lcl.quartz.job.SecondQuartzJob;
import com.lcl.quartz.service.JobService;
import com.lcl.quartz.util.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: JobController
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/1 15:41
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@RestController
@RequestMapping("/job")
public class JobController {
    @Autowired
    private JobService jobService;

    @PostMapping("/add1")
    public ApiResult add1(@RequestParam String jobName,
                          @RequestParam String cron) {
        jobService.addJobForJobId(jobName, FirstQuartzJob.class, cron);
        return ApiResult.of(ApiResult.ResultCode.OK, "添加成功", null);
    }

    @PostMapping("/add2")
    public ApiResult add2(@RequestParam String jobName,
                          @RequestParam String cron) {
        jobService.addJobForJobId(jobName, SecondQuartzJob.class, cron);
        return ApiResult.of(ApiResult.ResultCode.OK, "添加成功", null);
    }


    @PostMapping("/update")
    public ApiResult update(@RequestParam String jobName,
                            @RequestParam String cron) {
        jobService.updateJobCronForJobId(jobName, cron);
        return ApiResult.of(ApiResult.ResultCode.OK, "修改周期成功", null);
    }

    @PostMapping("/pause")
    public ApiResult pause(@RequestParam String jobName) {
        jobService.pauseJobForJobId(jobName);
        return ApiResult.of(ApiResult.ResultCode.OK, "暂停成功", null);
    }

    @PostMapping("/remove")
    public ApiResult remove(@RequestParam String jobName) {
        jobService.deleteJobForJobId(jobName);
        return ApiResult.of(ApiResult.ResultCode.OK, "删除成功", null);
    }

    @PostMapping("/resume")
    public ApiResult resume(@RequestParam String jobName) {
        jobService.resumeJobForJobId(jobName);
        return ApiResult.of(ApiResult.ResultCode.OK, "任务恢复成功", null);
    }

    @PostMapping("/start")
    public ApiResult start(@RequestParam String jobName) {
        jobService.startJobForJobId(jobName);
        return ApiResult.of(ApiResult.ResultCode.OK, "任务启动成功", null);
    }


}

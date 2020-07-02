package com.lcl.redis.controller;

import com.lcl.redis.pojo.User;
import com.lcl.redis.service.LockService;
import com.lcl.redis.util.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: TestController
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/1 9:23
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@RestController
@RequestMapping("/redis")
public class TestController {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private LockService lockService;

    /**
     * 测试发布订阅
     *
     * @param topic
     * @param user
     * @return
     */
    @PostMapping("/send")
    public ApiResult send(@RequestParam String topic, User user) {
        redisTemplate.convertAndSend(topic, user);
        return ApiResult.of(ApiResult.ResultCode.OK, "请求成功", null);
    }

    /**
     * 测试分布式锁是否生效,
     * 测试生效
     * @param id
     * @return
     */
    @GetMapping("/testlock/{id}")
    public ApiResult testlock(@PathVariable String id) {
        try {
            lockService.print(id);
            return ApiResult.of(ApiResult.ResultCode.OK, "请求成功", null);
        } catch (InterruptedException e) {
            return ApiResult.of(ApiResult.ResultCode.INTERNAL_SERVER_ERROR, "故障", null);
        }
    }

}

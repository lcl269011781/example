package com.lcl.ratelimitcluster.controller;

import com.lcl.ratelimitcluster.annotation.RateLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/10/14 14:03
 */
@RestController
public class TestController {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    // 10 秒中，可以访问10次
    @RateLimit(key = "test", time = 100, count = 10)
    @GetMapping("/test")
    public String luaLimiter() {
        RedisAtomicInteger entityIdCounter = new RedisAtomicInteger("entityIdCounter", redisTemplate.getConnectionFactory());

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());

        return date + " 累计访问次数：" + entityIdCounter.getAndIncrement();
    }

}

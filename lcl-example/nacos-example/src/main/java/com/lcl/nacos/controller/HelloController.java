package com.lcl.nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: HelloController
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/4/4 11:19
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@RestController
@RefreshScope //支持nacos动态刷新功能
public class HelloController {

    @Value("${config.info}")
    private String configuration;

    @GetMapping("/nacos/hello")
    public String hello(){
        return configuration;
    }

}

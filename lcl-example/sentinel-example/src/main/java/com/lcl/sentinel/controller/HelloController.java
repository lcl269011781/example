package com.lcl.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.lcl.sentinel.handler.MyHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/hello")
    public String hello() {
        return configuration;
    }

    /**
     * 热点key测试
     * blockHandler 处理的是sentinel配置异常，降级流控等。
     * @param p1
     * @param p2
     * @return
     */
    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        return "......testHotKey";
    }

    public String deal_testHotKey(String p1, String p2, BlockException e) {
        return "......deal_testHotKey";
    }

    /**
     * SentinelResource：指定哪个类处理失败返回结果，blockHandler指定哪个方法。
     * @return
     */
    @GetMapping("/testhandler")
    @SentinelResource(value = "testhandler",blockHandlerClass = MyHandler.class,blockHandler = "handler1")
    public String testhandler(){
        return "......testhandler";
    }

}

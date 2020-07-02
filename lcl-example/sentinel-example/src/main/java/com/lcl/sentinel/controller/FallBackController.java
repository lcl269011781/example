package com.lcl.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName: FallBackController
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/4/4 18:58
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@RestController
public class FallBackController {

    private static final String serverUrl = "http://nacos-example/";
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 测试 fallback,fallback处理的是业务上的异常
     *
     * @return
     */
    @GetMapping("/fallback/{p}")
//    @SentinelResource(value = "fallback") //没任何配置
//    @SentinelResource(value = "fallback",fallback = "handlerFallback") //fallback负责业务代码上的异常
//    @SentinelResource(value = "fallback",blockHandler = "handlerBlock") //block负责sentinel控制台配置违规
    @SentinelResource(value = "fallback",fallback = "handlerFallback",blockHandler = "handlerBlock") //结合配置，block优先级高
    public String fallback(@PathVariable Integer p) {
        if (p == 1) {
            throw new NullPointerException("ERROR");
        }
        return restTemplate.getForObject(serverUrl + "/nacos/hello", String.class);
    }

    public String handlerFallback(@PathVariable Integer p, Throwable e){
        return "p="+p+"出错了."+e;
    }

    public String handlerBlock(@PathVariable Integer p, BlockException e){
        return "p="+p+"出错了."+e;
    }

}

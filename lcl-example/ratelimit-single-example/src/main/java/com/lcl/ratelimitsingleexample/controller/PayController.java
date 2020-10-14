package com.lcl.ratelimitsingleexample.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.lcl.ratelimitsingleexample.service.MessageService;
import com.lcl.ratelimitsingleexample.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Author: Chunliang.Li
 * Date: 2020/10/14 10:22
 */
@RestController
public class PayController {
    private final Logger log = LoggerFactory.getLogger(PayController.class);
    /**
     * RateLimiter的create()方法中传入一个参数，表示以固定的速率2r/s，即以每秒2个令牌的速率向桶中放入令牌
     */
    private final RateLimiter rateLimiter = RateLimiter.create(1);
    @Autowired
    private MessageService messageService;
    @Autowired
    private PayService payService;

    @RequestMapping("/pay")
    public String pay() {
        //记录返回接口
        String result = "";
        //限流处理，客户端请求从桶中获取令牌，如果在500毫秒没有获取到令牌，则直接走服务降级处理
        boolean tryAcquire = rateLimiter.tryAcquire(100, TimeUnit.MILLISECONDS);
        if (!tryAcquire) {
            result = "请求过多，降级处理";
            log.info(result);
            return result;
        }
        int ret = payService.pay(BigDecimal.valueOf(100.0));
        if (ret > 0) {
            result = "支付成功";
            return result;
        }
        result = "支付失败，再试一次吧...";
        return result;
    }

}

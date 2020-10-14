package com.lcl.ratelimitsingleexample.service.impl;

import com.lcl.ratelimitsingleexample.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Description:
 * Author: Chunliang.Li
 * Date: 2020/10/14 10:20
 */
@Service
public class PayServiceImpl implements PayService {

    private static final Logger log = LoggerFactory.getLogger(PayServiceImpl.class);

    @Override
    public int pay(BigDecimal price) {
        log.info("===>>>支付成功:{}",price);
        return 1;
    }
}

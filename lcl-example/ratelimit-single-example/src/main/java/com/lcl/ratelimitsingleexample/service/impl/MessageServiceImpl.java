package com.lcl.ratelimitsingleexample.service.impl;

import com.lcl.ratelimitsingleexample.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Author: Chunliang.Li
 * Date: 2020/10/14 10:18
 */
@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Override
    public boolean sendMessage(String message) {
        log.info("===>>>消息发送成功:{}",message);
        return true;
    }
}

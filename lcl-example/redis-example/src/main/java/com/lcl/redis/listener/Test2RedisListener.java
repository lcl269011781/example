package com.lcl.redis.listener;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @ClassName: Test1RedisListener
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/1 9:13
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Component
@Slf4j
public class Test2RedisListener implements MessageListener {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        String json = JSONObject.toJSONString(message.toString());
        log.info("Test2RedisListener,通道：{},内容：{}",redisTemplate.getKeySerializer().deserialize(message.getChannel()),json);
    }
}

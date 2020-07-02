package com.lcl.redis.config;

import com.lcl.redis.listener.Test1RedisListener;
import com.lcl.redis.listener.Test2RedisListener;
import com.lcl.redis.util.RedisTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @ClassName: RedisSubConfig
 * @Description: redis发布订阅配置
 * @Author: Chunliang.Li
 * @Date: 2020/7/1 9:10
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Configuration
public class RedisSubConfig {

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;
    @Autowired
    private Test1RedisListener test1RedisListener;
    @Autowired
    private Test2RedisListener test2RedisListener;

    /**
     * 创建redis消息监听器
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(lettuceConnectionFactory);
        //注册， 将监听器与主题绑定。
        container.addMessageListener(test1RedisListener, new ChannelTopic(RedisTopic.TopicName.TOPE1));
        container.addMessageListener(test2RedisListener, new ChannelTopic(RedisTopic.TopicName.TOPE2));
        container.setTaskExecutor(threadPoolTaskScheduler());
        return container;
    }
    /**
     * 创建任务池，运行线程等待处理redis的消息
     */
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(20);
        taskScheduler.setThreadNamePrefix("REDIS-");
        return taskScheduler;
    }
}

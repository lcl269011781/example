package com.lcl.kafka.config;

import com.alibaba.fastjson.JSONObject;
import com.lcl.kafka.pojo.KafkaJson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;

/**
 * @ClassName: KafkaConfig
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/22 10:20
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Configuration
@Slf4j
public class KafkaConfig {

    /**
     * 当监听抛出异常的时候，则会自动调用异常处理器
     */
    @Bean
    public ConsumerAwareListenerErrorHandler consumerAwareListenerErrorHandler() {
        return new ConsumerAwareListenerErrorHandler() {
            @Override
            public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
                log.error("消费异常:{}", message.getPayload());
                return null;
            }

            @Override
            public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
                log.error("消费异常:{}", message.getPayload());
                return null;
            }
        };
    }

    /**
     * 配置监听者消息过滤器
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory(ConsumerFactory consumerFactory) {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory);
        // 被过滤的消息将被丢弃
        factory.setAckDiscarded(true);
        // 消息过滤策略
        factory.setRecordFilterStrategy(consumerRecord -> {
            KafkaJson message = JSONObject.parseObject(consumerRecord.value().toString(), KafkaJson.class);
            if ("111".equals(message.getContent())) {
                //返回true消息则被过滤
                return true;
            }
            return false;
        });
        return factory;
    }

}

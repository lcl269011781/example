package com.lcl.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: KafkaInitialConfiguration
 * @Description: kafka初始化主题分区设置类
 * @Author: Chunliang.Li
 * @Date: 2020/7/22 10:00
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Configuration
public class KafkaInitialConfiguration {
    /**
     * 初始化主题，并设置分区与副本数
     */
    @Bean
    public NewTopic initTopic(@Value("${kafka.topic.test1.name}") String topic,
                              @Value("${kafka.topic.test1.partition}") int partition,
                              @Value("${kafka.topic.test1.replication}") short replication) {
        return new NewTopic(topic, partition, replication);
    }

}

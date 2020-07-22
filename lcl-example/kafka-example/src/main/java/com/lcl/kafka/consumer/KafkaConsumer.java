package com.lcl.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: KafkaConsumer
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/22 10:08
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Component
@Slf4j
public class KafkaConsumer {
    //批量消费
//    @KafkaListener(groupId = "${kafka.topic.test1.group}", topics = {"${kafka.topic.test1.name}"})
//    public void onMessageTopic1(List<ConsumerRecord<?, ?>> list) {
//        list.parallelStream().forEach(record -> log.info("主题:{},分区:{},内容:{}", record.topic(), record.partition(), record.value()));
//    }

    @KafkaListener(groupId = "${kafka.topic.test1.group}", topics = {"${kafka.topic.test1.name}"},errorHandler = "consumerAwareListenerErrorHandler")
    public void onMessageTopic1(ConsumerRecord<?, ?> record) {
        log.info("主题:{},分区:{},内容:{}", record.topic(), record.partition(), record.value());
        throw new RuntimeException("123");
    }

    @KafkaListener(groupId = "${kafka.topic.test2.group}", topics = {"${kafka.topic.test2.name}"},containerFactory = "concurrentKafkaListenerContainerFactory")
    public void onMessageTopic2(ConsumerRecord<?, ?> record) {
        log.info("主题:{},分区:{},内容:{}", record.topic(), record.partition(), record.value());
    }
}

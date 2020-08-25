package com.lcl.rabbitmq.config;

import com.lcl.rabbitmq.util.RabbitMqNameManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Description：
 * Author: Chunliang.Li
 * Date: 2020/8/24 16:21
 **/
@Configuration
@EnableRabbit
@Slf4j
public class RabbitMqConfig {
    @Autowired
    AmqpAdmin amqpAdmin;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 发送端确认机制
     * publisher-confirm-type: simple
     * publisher-returns: true
     * template:
     *  mandatory: true
     */
    @PostConstruct
    public void initRabbitTemplate() {
        //发送到broker时回复
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> log.info("confirm:{},{},{}", correlationData.getId(), ack, cause));
        //正确投递到队列：  失败则回调
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> log.info("returnMessage:message:{},replycode:{},replyText:{},exchange:{},routingKey:{}", message, replyCode, replyText, exchange, routingKey));
    }

    @PostConstruct
    public void init() {
        amqpAdmin.declareExchange(new DirectExchange(RabbitMqNameManager.ExchangeName.TEST_EXCHANGE_1,
                true, false));
        amqpAdmin.declareQueue(new Queue(RabbitMqNameManager.QueueName.TEST_QUEUE_1,
                true, false, false));
        amqpAdmin.declareBinding(new Binding(RabbitMqNameManager.QueueName.TEST_QUEUE_1,
                Binding.DestinationType.QUEUE,
                RabbitMqNameManager.ExchangeName.TEST_EXCHANGE_1,
                RabbitMqNameManager.RoutingKey.TEST_ROUTINGKEY_1,
                null));
    }


}
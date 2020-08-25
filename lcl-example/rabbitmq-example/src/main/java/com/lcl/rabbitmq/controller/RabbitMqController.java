package com.lcl.rabbitmq.controller;

import com.lcl.rabbitmq.pojo.Order;
import com.lcl.rabbitmq.pojo.User;
import com.lcl.rabbitmq.util.ApiResult;
import com.lcl.rabbitmq.util.RabbitMqNameManager;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Description：
 * Author: Chunliang.Li
 * Date: 2020/8/24 16:36
 **/
@RestController
@RequestMapping("/rabbitmq")
public class RabbitMqController {

    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    RabbitTemplate rabbitTemplate;


    @GetMapping("/sendUser")
    public ApiResult<String> sendUser(@RequestParam String username,
                                      @RequestParam String password) {

        User user = new User();
        user.setId(UUID.randomUUID().toString()).setUsername(username).setPassword(password);
        //CorrelationData 执行消息的唯一ID
        rabbitTemplate.convertAndSend(RabbitMqNameManager.ExchangeName.TEST_EXCHANGE_1,
                RabbitMqNameManager.RoutingKey.TEST_ROUTINGKEY_1,
                user, new CorrelationData(user.getId()));

        return ApiResult.of(ApiResult.ResultCode.OK, "发送成功", null);
    }

    @GetMapping("/sendOrder")
    public ApiResult<String> sendOrder(@RequestParam String name) throws Exception {

        Order order = new Order();
        order.setId(UUID.randomUUID().toString()).setName(name);
        //CorrelationData 执行消息的唯一ID
        rabbitTemplate.convertAndSend(RabbitMqNameManager.ExchangeName.TEST_EXCHANGE_1,
                RabbitMqNameManager.RoutingKey.TEST_ROUTINGKEY_1,
                order, new CorrelationData(order.getId()));

        return ApiResult.of(ApiResult.ResultCode.OK, "发送成功", null);
    }

}

package com.lcl.rabbitmq.consumer;

import com.lcl.rabbitmq.pojo.Order;
import com.lcl.rabbitmq.pojo.User;
import com.lcl.rabbitmq.util.RabbitMqNameManager;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Description：
 * Author: Chunliang.Li
 * Date: 2020/8/24 16:59
 */
@RabbitListener(queues = RabbitMqNameManager.QueueName.TEST_QUEUE_1)
@Component
public class RabbitConsumer {

    //    @RabbitListener(queues = RabbitMqNameManager.QueueName.TEST_QUEUE_1)
    @RabbitHandler
    public void testListen1(Message message, User user, Channel channel) {
        System.out.println("===>>> 监听User类型");
        System.out.println(user);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        System.out.println(deliveryTag);
        try {
            if (deliveryTag % 2 == 0) {
                //basicAck成功签收处理回复  参数1表示监听到的消息条数，递增   参数2 是否批量签收
                channel.basicAck(deliveryTag, false);
                System.out.println("已签收");
            } else {
                //basicNack失败处理签收  参数3 未签收消息是否重新入队列
                channel.basicNack(deliveryTag, false, false);
                System.out.println("拒绝");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RabbitHandler
    public void testListen1(Order order) {
        System.out.println("===>>> 监听Order类型");
        System.out.println(order);
    }

}


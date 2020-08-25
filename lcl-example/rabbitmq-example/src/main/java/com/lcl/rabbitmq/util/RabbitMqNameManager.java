package com.lcl.rabbitmq.util;

/**
 * Description：RabbitMq路由 交换机 队列名称管理
 * Author: Chunliang.Li
 * Date: 2020/8/24 16:28
 **/
public class RabbitMqNameManager {


    public interface BindName{
        String TEST_BIND_1 = "TEST_BIND_1";

    }
    public interface RoutingKey {

        String TEST_ROUTINGKEY_1 = "TEST_ROUTINGKEY_1";

    }

    public interface ExchangeName {
        /**
         * 交换机1
         */
        String TEST_EXCHANGE_1 = "TEST_EXCHANGE_1";

    }

    public interface QueueName {
        /**
         * 队列1
         */
        String TEST_QUEUE_1 = "TEST_QUEUE_1";
    }

}

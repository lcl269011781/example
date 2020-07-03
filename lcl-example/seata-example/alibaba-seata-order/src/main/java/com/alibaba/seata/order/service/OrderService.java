package com.alibaba.seata.order.service;

import com.alibaba.seata.order.domain.Order;

public interface OrderService {

    void create(Order order);

}

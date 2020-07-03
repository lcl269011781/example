package com.alibaba.seata.order.service.impl;

import com.alibaba.seata.order.dao.OrderDao;
import com.alibaba.seata.order.domain.Order;
import com.alibaba.seata.order.feign.AccountService;
import com.alibaba.seata.order.service.OrderService;
import com.alibaba.seata.order.feign.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName: OrderServiceImpl
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/4/6 15:47
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;
    @Resource
    private AccountService accountService;
    @Resource
    private StorageService storageService;

    @GlobalTransactional(name = "test",rollbackFor = Exception.class)
    @Override
    public void create(Order order) {
        log.info("----->开始新建订单");
        //1.新建订单
        orderDao.create(order);

        log.info("----->订单微服务开始调用库存，做扣减库存数量");
        //2.扣减库存
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("----->订单微服务调用库存，做扣减库存数量结束");

        log.info("----->订单微服务开始调用账户，做扣钱");
        //3.扣减账户余额
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("----->订单微服务调用账户，做扣钱结束");

        //修改订单的状态，从0-1
        log.info("----->开始修改订单状态");
        //4.修改订单状态
        orderDao.update(order.getUserId(),0);
        log.info("----->结束修改订单状态");

        log.info("----->下单技术");

    }
}

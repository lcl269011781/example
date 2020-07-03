package com.alibaba.seata.order.controller;

import com.alibaba.seata.order.domain.Order;
import com.alibaba.seata.order.domain.R;
import com.alibaba.seata.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: OrderController
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/4/6 16:00
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@RestController
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/order/create")
    public R create(Order order){
        orderService.create(order);
        return new R(200,"创建订单完成");
    }

    @GetMapping("/test")
    public String testy(){
        return "ff";
    }

}

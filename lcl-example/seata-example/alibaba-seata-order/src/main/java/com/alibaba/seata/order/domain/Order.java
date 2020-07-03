package com.alibaba.seata.order.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @ClassName: Order
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/4/6 15:35
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;
    private Long userId;
    private Long productId;
    private Long count;
    private BigDecimal money;
    private Integer status;

}

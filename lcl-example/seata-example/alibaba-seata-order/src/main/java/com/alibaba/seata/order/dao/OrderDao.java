package com.alibaba.seata.order.dao;

import com.alibaba.seata.order.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
public interface OrderDao {

    /**
     * 新建订单
     * @param order
     * @return
     */
    int create(Order order);

    /**
     * 修改订单状态
     * @param userId
     * @param statue
     * @return
     */
    int update(@Param("userId")Long userId,@Param("status")Integer statue);

}

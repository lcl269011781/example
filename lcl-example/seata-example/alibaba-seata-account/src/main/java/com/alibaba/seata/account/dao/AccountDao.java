package com.alibaba.seata.account.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
public interface AccountDao {

    int update(@Param("userId")Long userId, @Param("money")BigDecimal money);

}

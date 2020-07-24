package com.lcl.distributedlock.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MysqlLockDao {

    int insert(@Param("lockName") String lockName, @Param("val") String val);

    int delete(@Param("lockName") String lockName, @Param("val") String val);

}

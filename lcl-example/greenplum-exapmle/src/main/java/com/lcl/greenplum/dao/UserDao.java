package com.lcl.greenplum.dao;

import com.lcl.greenplum.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

    List<User> selectAll();

}

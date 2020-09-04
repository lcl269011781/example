package com.lcl.greenplum.service;

import com.lcl.greenplum.pojo.User;

import java.util.List;

/**
 * Description：
 * Author: Chunliang.Li
 * Date: 2020/9/3 15:23
 **/
public interface UserService {

    List<User> queryAll();

}

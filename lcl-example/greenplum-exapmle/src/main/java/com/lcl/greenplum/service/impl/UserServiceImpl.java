package com.lcl.greenplum.service.impl;

import com.lcl.greenplum.dao.UserDao;
import com.lcl.greenplum.pojo.User;
import com.lcl.greenplum.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description：
 * Author: Chunliang.Li
 * Date: 2020/9/3 15:24
 **/
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public List<User> queryAll() {
        return userDao.selectAll();
    }
}

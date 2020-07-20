package com.lcl.shiro.service.impl;

import com.lcl.shiro.dao.UserDao;
import com.lcl.shiro.pojo.User;
import com.lcl.shiro.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @ClassName: LoginServiceImpl
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/8 8:53
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Resource
    private UserDao userDao;

    @Override
    public void login(User user) {
        User dbUser = userDao.selectByUsername(user.getUsername());

    }
}

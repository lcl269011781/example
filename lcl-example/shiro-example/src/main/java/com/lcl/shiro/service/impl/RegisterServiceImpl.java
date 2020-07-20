package com.lcl.shiro.service.impl;

import com.lcl.shiro.dao.UserDao;
import com.lcl.shiro.pojo.User;
import com.lcl.shiro.service.RegisterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @ClassName: RegisterServiceImpl
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/8 8:56
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class RegisterServiceImpl implements RegisterService {
    @Resource
    private UserDao registerDao;

    @Override
    public void register(User user) {
        registerDao.insert(user);
    }

    @Override
    public void addRole(String userId, String roleId) {
        registerDao.insertUserRoleRel(userId, roleId);
    }
}

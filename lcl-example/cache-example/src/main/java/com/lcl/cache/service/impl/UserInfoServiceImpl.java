package com.lcl.cache.service.impl;

import com.lcl.cache.dao.UserInfoDao;
import com.lcl.cache.pojo.UserInfo;
import com.lcl.cache.service.UserInfoService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description：
 * Author: Chunliang.Li
 * Date: 2020/8/21 11:59
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    UserInfoDao userInfoDao;

    @Cacheable(value = {"test"},key = "#id")
    @Override
    public UserInfo selectById(Long id) {
        return userInfoDao.selectById(id);
    }

    /**
     * @CacheEvict ： 缓存失效
     * allEntries:test下的全部清除
     */
//    @CacheEvict(value = {"test"},allEntries = true)
    @CacheEvict(value = "test",allEntries = true)
    @Override
    public void insert(UserInfo user) {
        userInfoDao.insert(user);
    }
    @Cacheable(value = {"test"},key = "#root.methodName")
    @Override
    public List<UserInfo> selectAll() {
        return userInfoDao.selectAll();
    }

    @Override
    public UserInfo selectByUsername(String username) {
        return userInfoDao.selectByUsername(username);
    }
}

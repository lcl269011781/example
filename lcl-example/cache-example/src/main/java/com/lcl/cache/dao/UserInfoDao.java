package com.lcl.cache.dao;

import com.lcl.cache.pojo.UserInfo;

import java.util.List;

public interface UserInfoDao {

    UserInfo selectById(Long id);

    int insert(UserInfo user);

    List<UserInfo> selectAll();

    UserInfo selectByUsername(String username);

    int update(UserInfo user);
}

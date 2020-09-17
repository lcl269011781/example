package com.lcl.cache.service;

import com.lcl.cache.pojo.UserInfo;

import java.util.List;

public interface UserInfoService {

    UserInfo selectById(Long id);

    void insert(UserInfo user);

    List<UserInfo> selectAll();

    UserInfo selectByUsername(String username);

    void update(UserInfo user);

}

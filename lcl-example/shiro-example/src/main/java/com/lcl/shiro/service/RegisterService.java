package com.lcl.shiro.service;

import com.lcl.shiro.pojo.User;

public interface RegisterService {
    /**
     * 注册
     * @param user
     */
    void register(User user);

    /**
     * 分配角色
     */
    void addRole(String userId,String roleId);
}

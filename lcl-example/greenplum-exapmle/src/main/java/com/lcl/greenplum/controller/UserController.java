package com.lcl.greenplum.controller;

import com.lcl.greenplum.pojo.User;
import com.lcl.greenplum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description：
 * Author: Chunliang.Li
 * Date: 2020/9/3 15:24
 **/
@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/showAll")
    public List<User> showAll() {
        return userService.queryAll();
    }

}

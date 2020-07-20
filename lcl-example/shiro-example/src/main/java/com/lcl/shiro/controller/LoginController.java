package com.lcl.shiro.controller;

import com.lcl.shiro.pojo.User;
import com.lcl.shiro.service.LoginService;
import com.lcl.shiro.util.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * @ClassName: LoginController
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/8 8:53
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@RestController
@Slf4j
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ApiResult login(@Valid User user){
//        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
//        Subject subject = SecurityUtils.getSubject();
//        subject.login(token);
        log.info("----login");
        return ApiResult.of(ApiResult.ResultCode.OK,"认证成功",null);
    }

}

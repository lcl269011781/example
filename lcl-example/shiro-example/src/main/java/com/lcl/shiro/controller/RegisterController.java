package com.lcl.shiro.controller;

import com.lcl.shiro.pojo.User;
import com.lcl.shiro.service.RegisterService;
import com.lcl.shiro.util.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @ClassName: RegisterController
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/8 8:55
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@RestController
@Slf4j
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public ApiResult register(@Valid User user) {
        try{
            registerService.register(user);
            return ApiResult.of(ApiResult.ResultCode.OK,"注册成功",null);
        }catch (Exception e){
            log.error("",e);
            return ApiResult.of(ApiResult.ResultCode.INTERNAL_SERVER_ERROR,"注册失败",null);
        }
    }

    /**
     * 分配角色
     */
    @PostMapping("/addRole")
    public ApiResult addRole(@RequestParam String userId,
                             @RequestParam String roleId){
        try{
            registerService.addRole(userId, roleId);
            return ApiResult.of(ApiResult.ResultCode.OK,"角色分配成功",null);
        }catch (Exception e){
            log.error("",e);
            return ApiResult.of(ApiResult.ResultCode.INTERNAL_SERVER_ERROR,"角色分配失败",null);
        }
    }

}

package com.lcl.cache.controller;

import com.lcl.cache.pojo.UserInfo;
import com.lcl.cache.service.UserInfoService;
import com.lcl.cache.util.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description：
 * Author: Chunliang.Li
 * Date: 2020/8/21 11:58
 **/
@RestController
@RequestMapping("/userinfo")
@Slf4j
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;

    @PostMapping("/add")
    public ApiResult<String> add(UserInfo userInfo) {
        try {
            userInfoService.insert(userInfo);
            return ApiResult.of(ApiResult.ResultCode.OK, "新建成功");
        } catch (Exception e) {
            log.error("", e);
            return ApiResult.of(ApiResult.ResultCode.INTERNAL_SERVER_ERROR, "新建失败");
        }
    }


    @GetMapping("/showById")
    public ApiResult<UserInfo> showById(@RequestParam Long id) {
        try {
            return ApiResult.of(ApiResult.ResultCode.OK, "查询成功", userInfoService.selectById(id));
        } catch (Exception e) {
            log.error("", e);
            return ApiResult.of(ApiResult.ResultCode.INTERNAL_SERVER_ERROR, "查询失败");
        }
    }

    @GetMapping("/showByUsername")
    public ApiResult<UserInfo> showByUsername(@RequestParam String username) {
        try {
            return ApiResult.of(ApiResult.ResultCode.OK, "查询成功", userInfoService.selectByUsername(username));
        } catch (Exception e) {
            log.error("", e);
            return ApiResult.of(ApiResult.ResultCode.INTERNAL_SERVER_ERROR, "查询失败");
        }
    }

    @GetMapping("/showAll")
    public ApiResult<List<UserInfo>> showAll() {
        try {
            return ApiResult.of(ApiResult.ResultCode.OK, "查询成功", userInfoService.selectAll());
        } catch (Exception e) {
            log.error("", e);
            return ApiResult.of(ApiResult.ResultCode.INTERNAL_SERVER_ERROR, "查询失败");
        }
    }
}

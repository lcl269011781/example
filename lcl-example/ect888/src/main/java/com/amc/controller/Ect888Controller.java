package com.amc.controller;

import com.amc.pojo.Json209;
import com.amc.pojo.Json239;
import com.amc.pojo.req.Ect209Req;
import com.amc.pojo.req.Ect239Req;
import com.amc.service.Ect209Service;
import com.amc.service.Ect239Service;
import com.amc.utils.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 华夏项目系统调用接口
 * Author: Chunliang.Li
 * Date: 2020/11/12 9:23
 **/
@RestController
@RequestMapping("/v1/ect888")
@Api(tags = "ect接口")
public class Ect888Controller {

    private static final Logger log = LoggerFactory.getLogger(Ect888Controller.class);

    @Autowired
    private Ect209Service<Ect209Req> ect209Service;

    @Autowired
    private Ect239Service<Ect239Req> ect239Service;

    @ApiOperation(value = "实名认证209", notes = "", httpMethod = "GET")
    @GetMapping("/getJson209")
    public ApiResult<Json209> getJson209(Ect209Req ect209Req) {
        try {
            return ApiResult.of(ApiResult.ResultCode.OK, ect209Service.get209Result(ect209Req));
        } catch (Exception e) {
            log.error("===>209接口异常:", e);
            return ApiResult.of(ApiResult.ResultCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "手机三要素239", notes = "", httpMethod = "GET")
    @GetMapping("/getJson239")
    public ApiResult<Json239> getJson239( Ect239Req ect239Req) {
        try {
            return ApiResult.of(ApiResult.ResultCode.OK, ect239Service.get239Result(ect239Req));
        } catch (Exception e) {
            log.error("===>239接口异常:", e);
            return ApiResult.of(ApiResult.ResultCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}

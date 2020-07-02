package com.lcl.dataxexample.controller;

import com.lcl.dataxexample.service.DataxService;
import com.lcl.dataxexample.util.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: DataxController
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/2 13:54
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@RestController
@RequestMapping("/datax")
@Slf4j
public class DataxController {
    @Autowired
    private DataxService dataxService;

    @PostMapping("/start/{id}")
    public ApiResult start(@PathVariable String id) {
        try {
            dataxService.exec(id);
            return ApiResult.of(ApiResult.ResultCode.OK, "启动成功", null);
        } catch (Exception e) {
            log.error("", e);
            return ApiResult.of(ApiResult.ResultCode.INTERNAL_SERVER_ERROR, "启动失败", null);
        }
    }

}

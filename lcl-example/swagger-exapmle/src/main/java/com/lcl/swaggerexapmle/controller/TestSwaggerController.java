package com.lcl.swaggerexapmle.controller;

import com.lcl.swaggerexapmle.pojo.Test;
import com.lcl.swaggerexapmle.util.ApiResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: TestSwaggerController
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/1 16:28
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@RestController
@RequestMapping("/swagger")
public class TestSwaggerController {

    @GetMapping("/test")
    @ApiOperation(value = "测试",notes = "",httpMethod = "GET")
    public ApiResult<Test> test() {
        return ApiResult.of(ApiResult.ResultCode.OK, "666", new Test("888"));
    }

}

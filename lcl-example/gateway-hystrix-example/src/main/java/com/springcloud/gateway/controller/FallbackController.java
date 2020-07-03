package com.springcloud.gateway.controller;

import com.springcloud.gateway.util.ApiResult;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: FallbackController
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/3/8 18:21
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@RestController
public class FallbackController {

    @GetMapping("/fallback")
    public ApiResult fallback() {
        return ApiResult.of(ApiResult.ResultCode.INTERNAL_SERVER_ERROR, "网管熔断，服务不可用", null);
    }
}

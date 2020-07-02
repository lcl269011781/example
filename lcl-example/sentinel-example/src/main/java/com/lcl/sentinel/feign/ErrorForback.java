package com.lcl.sentinel.feign;

import org.springframework.stereotype.Component;

/**
 * @ClassName: ErrorForback
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/3/5 10:28
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Component
public class ErrorForback implements TestFeign {
    @Override
    public String hello() {
        return "调用nacos-server接口超时";
    }
}

package com.lcl.sentinel.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName: TestFeign
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/3/5 10:28
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@FeignClient(value = "nacos-example",fallback = ErrorForback.class)
public interface TestFeign {

    @GetMapping("/nacos/hello")
    String hello();

}

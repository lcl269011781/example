package com.lcl.sentinel.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName: TestSentinel
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/3/5 10:27
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@RestController
public class TestSentinel {
    @Resource
    private TestFeign testFeign;

    @GetMapping("/feign/hello")
    public String hello() {
        return testFeign.hello();
    }

}

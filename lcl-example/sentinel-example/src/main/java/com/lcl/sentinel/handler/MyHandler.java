package com.lcl.sentinel.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @ClassName: MyHandler
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/4/4 18:37
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
public class MyHandler {
    /**
     * 解耦式写法，sentinel降级限流快速失败处理
     * @return
     */
    public static String handler1(BlockException e) {
        return "error,handler---1";
    }

    public static String handler2(BlockException e) {
        return "error,handler---2";
    }

}

package com.amc.service;

import java.util.Map;

/**
 * Description: 顶层接口
 * Author: Chunliang.Li
 * Date: 2020/11/12 9:32
 **/
public interface EctService<T> {
    /**
     * 构造参数
     * @param ectReq 用户输入参数
     */
    Map<String, String> buildParams(T ectReq);

    /**
     * 调用ect接口
     * @param ectReq 输入参数
     * @return 接口返回值
     */
    String doWork(T ectReq);

}

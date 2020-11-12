package com.amc.service;

import com.amc.pojo.Json209;
import com.amc.pojo.req.Ect209Req;
/**
 * Description: 实名认证209接口
 * Author: Chunliang.Li
 * Date: 2020/11/12 9:32
 **/
public interface Ect209Service<T> extends EctService<T>{
    /**
     * 得到ect209调用接口
     * @param ect209Req 请求参数
     */
    Json209 get209Result(Ect209Req ect209Req);

}

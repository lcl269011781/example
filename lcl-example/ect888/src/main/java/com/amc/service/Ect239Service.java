package com.amc.service;

import com.amc.pojo.Json239;
import com.amc.pojo.req.Ect239Req;

/**
 * Description: 手机号三要素239接口
 * Author: Chunliang.Li
 * Date: 2020/11/12 9:32
 **/
public interface Ect239Service<T> extends EctService<T> {
    /**
     * 得到ect239调用接口
     *
     * @param ect239Req 请求参数
     */
    Json239 get239Result(Ect239Req ect239Req);

}

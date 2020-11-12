package com.amc.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

/**
 * Description:
 * Author: Chunliang.Li
 * Date: 2020/11/12 10:24
 **/
public class ResultUtil {
    /**
     * map转实体
     * @param srcData map
     * @param desType 实体
     * @param <T> 泛型
     * @return 返回实体类
     */
    public static <T> T mapToBean(Map<String, String> srcData, Class<T> desType) {
        if (srcData == null || desType == null) {
            return null;
        }
        try {
            T desData = desType.newInstance();
            BeanUtils.populate(desData, srcData);
            return desData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

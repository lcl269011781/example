package com.amc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description: 时间工具类
 * Author: Chunliang.Li
 * Date: 2020/11/12 12:11
 **/
public class DateUtil {

    public static String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

}

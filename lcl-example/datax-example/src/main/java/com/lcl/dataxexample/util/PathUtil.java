package com.lcl.dataxexample.util;

/**
 * @ClassName: PathUtil
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/2 10:25
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
public class PathUtil {

    public static String getCurrentClasspath() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String currentClasspath = classLoader.getResource("").getPath();
        // 当前操作系统
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            // 删除path中最前面的/
            currentClasspath = currentClasspath.substring(1);
        }
        return currentClasspath;
    }

}

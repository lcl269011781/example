package com.lcl.dataxexample.datax;

import com.lcl.dataxexample.util.PathUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;

/**
 * @ClassName: DataxStart
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/1 18:16
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Slf4j
public class DataxStart {
    /**
     * 启动
     *
     * @param jobPath
     * @param jvm
     */
    public static void start(String jobPath, String jvm) throws Throwable {
        System.setProperty("datax.home", PathUtil.getCurrentClasspath() + "/datax");
        System.setProperty("now", LocalTime.now().toString());// 替换job中的占位符
        String[] datxArgs = {"-job", jobPath, "-mode", "standalone", "-jobid", "-1", "-jvm", jvm};
        NewEngine.entry(datxArgs);
    }

}

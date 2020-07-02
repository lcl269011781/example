package com.lcl.dataxexample.datax.pojo.job.setting.errorlimit;

import lombok.Data;

/**
 * @ClassName: ErrorLimit
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/2 9:00
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Data
public class ErrorLimit {
    private Integer record = 0;
    private Double percentage = 0.00;
}

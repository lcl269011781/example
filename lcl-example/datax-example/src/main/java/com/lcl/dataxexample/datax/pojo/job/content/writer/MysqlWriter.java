package com.lcl.dataxexample.datax.pojo.job.content.writer;

import com.lcl.dataxexample.datax.pojo.job.content.writer.parameter.MysqlWriterParameter;
import lombok.Data;

/**
 * @ClassName: MysqlWriter
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/2 9:08
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Data
public class MysqlWriter implements Writer {
    private String name = "mysqlwriter";
    private MysqlWriterParameter parameter;
}

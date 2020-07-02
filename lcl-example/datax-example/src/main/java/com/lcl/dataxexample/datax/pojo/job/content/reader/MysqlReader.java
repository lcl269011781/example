package com.lcl.dataxexample.datax.pojo.job.content.reader;

import com.lcl.dataxexample.datax.pojo.job.content.reader.parameter.MysqlReaderParameter;
import lombok.Data;

/**
 * @ClassName: MysqlReader
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/2 9:02
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Data
public class MysqlReader implements Reader {

    private String name = "mysqlreader";
    private MysqlReaderParameter parameter;

}

package com.lcl.dataxexample.datax.pojo.job.content.writer.parameter.connection;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: MysqlWriterConnection
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/2 9:06
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Data
public class MysqlWriterConnection {
    private String jdbcUrl;
    private List<String> table = new ArrayList<>();

}

package com.lcl.dataxexample.datax.pojo.job.content.reader.parameter.connection;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: MysqlReaderConnection
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/2 9:04
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Data
public class MysqlReaderConnection {
    private List<String> querySql = new ArrayList<>();
    private List<String> jdbcUrl = new ArrayList<>();

}

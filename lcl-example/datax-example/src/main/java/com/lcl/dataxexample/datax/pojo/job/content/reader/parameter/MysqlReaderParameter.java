package com.lcl.dataxexample.datax.pojo.job.content.reader.parameter;

import com.lcl.dataxexample.datax.pojo.job.content.reader.parameter.connection.MysqlReaderConnection;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: MysqlParameter
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/2 9:03
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Data
public class MysqlReaderParameter {

    private String username;
    private String password;
    private List<MysqlReaderConnection> connection = new ArrayList<>();

}

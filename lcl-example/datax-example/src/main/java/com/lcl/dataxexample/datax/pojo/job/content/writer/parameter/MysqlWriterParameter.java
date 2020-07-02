package com.lcl.dataxexample.datax.pojo.job.content.writer.parameter;

import com.lcl.dataxexample.datax.pojo.job.content.writer.parameter.connection.MysqlWriterConnection;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: MysqlWriterParameter
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/2 9:05
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Data
public class MysqlWriterParameter {
    private String writeMode;
    private String username;
    private String password;
    private List<String> column;
    private List<String> session;
    private List<String> preSql;
    private List<String> postSql;
    private List<MysqlWriterConnection> connection = new ArrayList<>();


}

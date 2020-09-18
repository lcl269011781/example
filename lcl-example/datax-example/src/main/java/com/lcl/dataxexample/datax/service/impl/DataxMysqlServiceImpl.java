package com.lcl.dataxexample.datax.service.impl;

import com.lcl.dataxexample.datax.pojo.job.content.reader.MysqlReader;
import com.lcl.dataxexample.datax.pojo.job.content.reader.parameter.MysqlReaderParameter;
import com.lcl.dataxexample.datax.pojo.job.content.reader.parameter.connection.MysqlReaderConnection;
import com.lcl.dataxexample.datax.pojo.job.content.writer.MysqlWriter;
import com.lcl.dataxexample.datax.pojo.job.content.writer.parameter.MysqlWriterParameter;
import com.lcl.dataxexample.datax.pojo.job.content.writer.parameter.connection.MysqlWriterConnection;
import com.lcl.dataxexample.datax.service.DataxMysqlService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

/**
 * @ClassName: DataxMysqlServiceImpl
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/2 9:40
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Service
public class DataxMysqlServiceImpl implements DataxMysqlService {
    @Override
    public MysqlReader generaterReaderTemplate() {
        MysqlReaderConnection connection = new MysqlReaderConnection();
        connection.getQuerySql().add("SELECT TABLE_CATALOG,TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,ORDINAL_POSITION,COLUMN_DEFAULT,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH,CHARACTER_OCTET_LENGTH,\n" +
                "NUMERIC_PRECISION,NUMERIC_SCALE,DATETIME_PRECISION,CHARACTER_SET_NAME,COLLATION_NAME,COLUMN_TYPE,COLUMN_KEY,EXTRA,PRIVILEGES,COLUMN_COMMENT,GENERATION_EXPRESSION,\n" +
                "SRS_ID FROM COLUMNS");
        connection.getJdbcUrl().add("jdbc:mysql:///information_schema?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8");

        MysqlReaderParameter parameter = new MysqlReaderParameter();
        parameter.setUsername("root");
        parameter.setPassword("123456");
        parameter.getConnection().add(connection);

        MysqlReader reader = new MysqlReader();
        reader.setParameter(parameter);
        return reader;
    }

    @Override
    public MysqlWriter generaterWriterTemplate() {
        MysqlWriterConnection connection = new MysqlWriterConnection();
        connection.setJdbcUrl("jdbc:mysql:///datax?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8");
        connection.getTable().add("columns");

        MysqlWriterParameter parameter = new MysqlWriterParameter();
        parameter.setUsername("root");
        parameter.setPassword("123456");
        parameter.setColumn(Arrays.asList("TABLE_CATALOG,TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,ORDINAL_POSITION,COLUMN_DEFAULT,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH,CHARACTER_OCTET_LENGTH, NUMERIC_PRECISION,NUMERIC_SCALE,DATETIME_PRECISION,CHARACTER_SET_NAME,COLLATION_NAME,COLUMN_TYPE,COLUMN_KEY,EXTRA,PRIVILEGES,COLUMN_COMMENT,GENERATION_EXPRESSION, SRS_ID".split(",")));
        parameter.setPostSql(null);
        parameter.setPreSql(Collections.singletonList("delete from columns"));
        parameter.setWriteMode("insert");
        parameter.setSession(null);
        parameter.getConnection().add(connection);

        MysqlWriter writer = new MysqlWriter();
        writer.setParameter(parameter);

        return writer;
    }
}

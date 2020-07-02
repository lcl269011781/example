package com.lcl.dataxexample.datax.service;

import com.lcl.dataxexample.datax.pojo.job.content.reader.MysqlReader;
import com.lcl.dataxexample.datax.pojo.job.content.writer.MysqlWriter;

public interface DataxMysqlService {

    MysqlReader generaterReaderTemplate();
    MysqlWriter generaterWriterTemplate();

}

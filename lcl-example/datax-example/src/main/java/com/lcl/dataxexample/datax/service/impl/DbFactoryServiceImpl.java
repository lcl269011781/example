package com.lcl.dataxexample.datax.service.impl;

import com.lcl.dataxexample.datax.pojo.job.content.reader.Reader;
import com.lcl.dataxexample.datax.pojo.job.content.writer.Writer;
import com.lcl.dataxexample.datax.service.DataxMysqlService;
import com.lcl.dataxexample.datax.service.DbFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: DbFactoryServiceImpl
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/2 9:39
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Service
public class DbFactoryServiceImpl implements DbFactoryService {
    @Autowired
    private DataxMysqlService dataxMysqlService;

    @Override
    public Reader generaterReader(int code) {
        switch (code) {
            //mysql
            case 1:
                return dataxMysqlService.generaterReaderTemplate();
            //oracle
            case 2:
                return null;
            default:
                return null;
        }
    }

    @Override
    public Writer generaterWriter(int code) {
        switch (code) {
            //mysql
            case 1:
                return dataxMysqlService.generaterWriterTemplate();
            //oracle
            case 2:
                return null;
            default:
                return null;
        }
    }
}

package com.lcl.dataxexample.datax.service;

import com.lcl.dataxexample.datax.pojo.job.content.reader.Reader;
import com.lcl.dataxexample.datax.pojo.job.content.writer.Writer;

public interface DbFactoryService {

    /**
     *  生成读取端
     * @param code 数据库类型代码 DataxSupportDbType
     * @return
     */
    Reader generaterReader(int code);
    /**
     *  生成写入端
     * @param code 数据库类型代码 DataxSupportDbType
     * @return
     */
    Writer generaterWriter(int code);
}

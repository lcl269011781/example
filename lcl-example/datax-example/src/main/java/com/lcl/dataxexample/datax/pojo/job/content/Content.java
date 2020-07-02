package com.lcl.dataxexample.datax.pojo.job.content;

import com.lcl.dataxexample.datax.pojo.job.content.reader.Reader;
import com.lcl.dataxexample.datax.pojo.job.content.writer.Writer;
import lombok.Data;

/**
 * @ClassName: Content
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/2 9:00
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Data
public class Content {

    private Reader reader;
    private Writer writer;

}

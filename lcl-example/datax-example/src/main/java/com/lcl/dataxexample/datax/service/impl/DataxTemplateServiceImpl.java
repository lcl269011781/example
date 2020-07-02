package com.lcl.dataxexample.datax.service.impl;

import com.lcl.dataxexample.datax.pojo.DataxJson;
import com.lcl.dataxexample.datax.pojo.job.Job;
import com.lcl.dataxexample.datax.pojo.job.content.Content;
import com.lcl.dataxexample.datax.pojo.job.setting.Setting;
import com.lcl.dataxexample.datax.service.DataxTemplateService;
import com.lcl.dataxexample.datax.service.DbFactoryService;
import com.lcl.dataxexample.util.DataxSupportDbType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: DataxTemplateServiceImpl
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/2 9:11
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Service
public class DataxTemplateServiceImpl implements DataxTemplateService {
    @Autowired
    private DbFactoryService dbFactoryService;

    @Override
    public DataxJson generateDataxJobJson() {
        Setting setting = new Setting();
        this.generateSetting(setting);

        Content content = new Content();
        this.generaterReader(content, DataxSupportDbType.DbType.MYSQL.getCode());
        this.generaterWriter(content, DataxSupportDbType.DbType.MYSQL.getCode());

        Job job = new Job();
        job.setSetting(setting);
        job.getContent().add(content);

        DataxJson json = new DataxJson();
        json.setJob(job);

        return json;
    }

    /**
     * 生成Setting配置，线程数、读取速率、一次读取大小、一次写入大小
     *
     * @param setting
     */
    private void generateSetting(Setting setting) {
        setting.getSpeed().put("batchSize", 1024);
        setting.getSpeed().put("channel", 2);
        setting.getSpeed().put("byte", 10485760);
        setting.getSpeed().put("record", 10000);
    }

    private void generaterReader(Content content, int code) {
        content.setReader(dbFactoryService.generaterReader(code));
    }

    private void generaterWriter(Content content, int code) {
        content.setWriter(dbFactoryService.generaterWriter(code));
    }

}

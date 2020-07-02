package com.lcl.dataxexample.datax.pojo.job;

import com.lcl.dataxexample.datax.pojo.job.content.Content;
import com.lcl.dataxexample.datax.pojo.job.setting.Setting;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Job
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/2 8:56
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Data
public class Job {
    private Setting setting;
    private List<Content> content = new ArrayList<>();
}

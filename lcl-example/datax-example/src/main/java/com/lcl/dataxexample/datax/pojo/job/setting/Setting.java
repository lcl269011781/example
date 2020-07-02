package com.lcl.dataxexample.datax.pojo.job.setting;

import com.lcl.dataxexample.datax.pojo.job.setting.errorlimit.ErrorLimit;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: Setting
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/2 8:57
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Data
public class Setting {
    private Map<String,Object> speed = new HashMap<>();
    private ErrorLimit errorLimit = new ErrorLimit();

    {
        //-1 不限制
        speed.put("record",-1);
        speed.put("batchSize",2048);
        speed.put("channel",2);
        speed.put("byte",10485760);
    }

}

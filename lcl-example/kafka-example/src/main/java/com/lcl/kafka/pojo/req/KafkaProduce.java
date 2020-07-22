package com.lcl.kafka.pojo.req;

import com.lcl.kafka.pojo.KafkaJson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: KafkaProduce
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/22 10:17
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaProduce {

    private String topic;
    private int partition;

}



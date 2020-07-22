package com.lcl.kafka.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Date;
import java.util.UUID;

/**
 * @ClassName: KafkaJson
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/22 10:16
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KafkaJson{
    private String id = UUID.randomUUID().toString().replace("-", "");
    private String content;
    private Date createDate = new Date();
}

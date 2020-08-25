package com.lcl.rabbitmq.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Description：
 * Author: Chunliang.Li
 * Date: 2020/8/25 9:12
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class Order {

    private String id;
    private String name;


}

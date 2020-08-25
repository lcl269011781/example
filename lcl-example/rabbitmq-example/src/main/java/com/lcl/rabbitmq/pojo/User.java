package com.lcl.rabbitmq.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Description：
 * Author: Chunliang.Li
 * Date: 2020/8/24 16:51
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class User implements Serializable {

    private String id;
    private String username;
    private String password;

}

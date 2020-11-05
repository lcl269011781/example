package com.lcl.practice.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Description：
 * Author: Chunliang.Li
 * Date: 2020/8/24 13:23
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true) //支持链式写法
//@ToString
public class User {

    private int id;
    private String username;
    private String password;
    private Boolean boo;

}

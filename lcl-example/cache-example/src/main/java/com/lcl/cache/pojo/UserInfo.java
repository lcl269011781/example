package com.lcl.cache.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description：
 * Author: Chunliang.Li
 * Date: 2020/8/21 11:25
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo  {

    private Long id;
    private String username;
    private String password;
    private String others;

}

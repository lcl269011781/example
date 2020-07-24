package com.lcl.distributedlock.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: MysqlLock
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/23 15:16
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MysqlLock {

    private String lockName;
    private String val;

}

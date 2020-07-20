package com.lcl.shiro.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: Permissions
 * @Description: 权限
 * @Author: Chunliang.Li
 * @Date: 2020/7/8 8:38
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permissions {
    private String id;
    private String permissionsName;
}

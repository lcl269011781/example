package com.lcl.shiro.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: User
 * @Description: 用户
 * @Author: Chunliang.Li
 * @Date: 2020/7/8 8:36
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String id;
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;
    private Integer isLock;
    private String token;
    private List<Role> roles;


}

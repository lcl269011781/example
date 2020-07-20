package com.lcl.shiro.dao;

import com.lcl.shiro.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName: RegisterDao
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/8 8:57
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
public interface UserDao {

    int insert(User user);

    int insertUserRoleRel(@Param("userId") String userId,@Param("roleId") String roleId);

    User selectByUsername(@Param("username")String username);

}

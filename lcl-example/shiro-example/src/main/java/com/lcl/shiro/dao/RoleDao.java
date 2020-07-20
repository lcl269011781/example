package com.lcl.shiro.dao;

import com.lcl.shiro.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDao {

    List<Role> selectByUserId(@Param("userId")String userId);

}

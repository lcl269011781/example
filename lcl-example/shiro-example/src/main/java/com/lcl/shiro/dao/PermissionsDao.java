package com.lcl.shiro.dao;

import com.lcl.shiro.pojo.Permissions;
import org.apache.ibatis.annotations.Param;

public interface PermissionsDao {

    Permissions selectByRoleId(@Param("roleId")String roleId);

}

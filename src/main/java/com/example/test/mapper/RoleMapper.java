package com.example.test.mapper;

import com.example.test.entity.Role;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface RoleMapper {

    @Select("SELECT * FROM role WHERE role_id IN (SELECT role_id FROM user_role WHERE user_id=#{user_id})")
    @Results({
            @Result(id = true, property = "role_id", column = "role_id"),
            @Result(property = "role_name", column = "role_name"),
            @Result(property = "permissions", column = "role_id", javaType = java.util.List.class, many = @Many(select = "com.example.test.mapper.PermissionMapper.findPermission")),
    })
    List<Role> findAllRole(Integer user_id);
}

package com.example.test.mapper;

import com.example.test.entity.User;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {

    @Select("select * from user")
    List<User> list();

    @Select("select * from user where name=#{username}")
    @Results({
            @Result(id = true, property = "user_id", column = "user_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "password", column = "password"),
            @Result(property = "roles", column = "user_id", javaType = java.util.List.class, many = @Many(select = "com.example.test.mapper.RoleMapper.findAllRole")),
    })
    User findUserByName(String username);
}

package com.example.test.mapper;

import com.example.test.entity.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionMapper {

    @Select("select * from permission where id in (select permission_id from role_permission where role_id=#{role_id})")
    List<Permission> findPermission(Integer role_id);
}

package com.example.test.entity;

import java.io.Serializable;

public class Permission implements Serializable {
    private Integer id;
    private String permission_name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermission_name() {
        return permission_name;
    }

    public void setPermission_name(String permission_name) {
        this.permission_name = permission_name;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", permission_name='" + permission_name + '\'' +
                '}';
    }
}

package com.example.test;

import com.example.test.entity.Permission;
import com.example.test.entity.Role;
import com.example.test.entity.User;
import com.example.test.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
        User user = userService.findUserByName("zhangsan");
        List<Role> roleList = user.getRoles();
        for (Role role : roleList) {
            List<Permission> permissionList = role.getPermissions();
            for (Permission permission : permissionList) {
                System.out.println(role.getRole_name() + " : " + permission.getPermission_name());
            }
        }
    }

}

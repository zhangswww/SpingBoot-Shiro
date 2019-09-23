package com.example.test.service.impl;

import com.example.test.entity.User;
import com.example.test.mapper.UserMapper;
import com.example.test.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceimpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> list() {
        /* 取出Controller层中set进去的session值 */
        Session session = SecurityUtils.getSubject().getSession();
        Object val = session.getAttribute("test");
        System.out.println(val);
        return userMapper.list();
    }

    @Override
    public User findUserByName(String username) {
        return userMapper.findUserByName(username);
    }
}

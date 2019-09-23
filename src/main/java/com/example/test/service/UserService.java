package com.example.test.service;

import com.example.test.entity.User;

import java.util.List;

public interface UserService {

    List<User> list();

    User findUserByName(String username);
}

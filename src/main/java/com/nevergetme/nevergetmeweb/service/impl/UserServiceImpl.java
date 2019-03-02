package com.nevergetme.nevergetmeweb.service.impl;

import com.nevergetme.nevergetmeweb.bean.User;
import com.nevergetme.nevergetmeweb.mapper.UserMapper;
import com.nevergetme.nevergetmeweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> findAllUser() {
        List<User> list = userMapper.findAll();
        return list;
    }

    @Override
    public List<User> findUser(String username) {
        List<User> list = userMapper.findUser(username);
        return list;
    }
}

package com.nevergetme.nevergetmeweb.service.impl;

import com.nevergetme.nevergetmeweb.bean.User;
import com.nevergetme.nevergetmeweb.mapper.UserMapper;
import com.nevergetme.nevergetmeweb.service.UserService;
import com.nevergetme.nevergetmeweb.utility.ContentUtility;
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

    @Override
    public User findUserByUserId(Integer id) {
        return userMapper.findUserByUserId(id);
    }

    @Override
    public User findUserByUserName(String username) {
        return null;
    }

    @Override
    public User findUserByUserNameAndPassword(String username, String password) {
        try {
            return userMapper.findUserByUserNameAndPassword(username, ContentUtility.encodeByMd5(password));
        }catch (Exception e){
            return null;
        }
    }
    @Override
    public User findUserByUserEmailAndPassword(String email, String password) {
        try {
            return userMapper.findUserByUserEmailAndPassword(email, ContentUtility.encodeByMd5(password));
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public User findUserByPhone(String phone) {
        return userMapper.findUserByPhone(phone);
    }

    @Override
    public void createUser(User user) {
        userMapper.createUser(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userMapper.findUserByEmail(email);
    }

    @Override
    public void updateUserRoleById(int id,int role){
        userMapper.updateUserRoleById(id,role);
    }
}

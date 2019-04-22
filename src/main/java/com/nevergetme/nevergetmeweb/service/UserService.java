package com.nevergetme.nevergetmeweb.service;

import com.nevergetme.nevergetmeweb.bean.User;

import java.util.List;

public interface UserService {
    public List<User> findAllUser();
    public List<User> findUser(String username);
    public User findUserByUserId(Integer id);
    public User findUserByUserName(String username);
    public User findUserByPhone(String phone);

    //public List<User> findExistUser();
}

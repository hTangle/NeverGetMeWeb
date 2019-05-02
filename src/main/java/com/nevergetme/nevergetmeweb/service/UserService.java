package com.nevergetme.nevergetmeweb.service;

import com.nevergetme.nevergetmeweb.bean.User;

import java.util.List;

public interface UserService {
    public List<User> findAllUser();
    public List<User> findUser(String username);
    public User findUserByUserId(Integer id);
    public User findUserByUserName(String username);
    public User findUserByUserNameAndPassword(String username,String password);
    public User findUserByPhone(String phone);
    public void createUser(User user);

    //public List<User> findExistUser();
}

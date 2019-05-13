package com.nevergetme.nevergetmeweb.service;

import com.nevergetme.nevergetmeweb.bean.User;

import java.util.List;

public interface UserService {
    List<User> findAllUser();
    List<User> findUser(String username);
    User findUserByUserId(Integer id);
    User findUserByUserName(String username);
    User findUserByUserNameAndPassword(String username,String password);
    User findUserByUserEmailAndPassword(String email,String password);
    User findUserByPhone(String phone);
    void createUser(User user);
    User findUserByEmail(String email);
    void updateUserRoleById(int id,int role);
    //public List<User> findExistUser();
}

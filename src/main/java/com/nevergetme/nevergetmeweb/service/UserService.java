package com.nevergetme.nevergetmeweb.service;

import com.nevergetme.nevergetmeweb.bean.User;

import java.util.List;

public interface UserService {
    public List<User> findAllUser();
    public List<User> findUser(String username);
    //public List<User> findExistUser();
}

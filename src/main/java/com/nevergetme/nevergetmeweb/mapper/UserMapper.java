package com.nevergetme.nevergetmeweb.mapper;

import com.nevergetme.nevergetmeweb.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAll();
    List<User> findUser(String username);
    User findUserByUserId(Integer id);
//    User findUserByUserName(String username);
    User findUserByPhone(String phone);
    void createUser(User user);
    User findUserByUserNameAndPassword(String username,String password);
    User findUserByUserEmailAndPassword(String email,String password);
    User findUserByEmail(String email);
    void updateUserRoleById(int id,int role);
}

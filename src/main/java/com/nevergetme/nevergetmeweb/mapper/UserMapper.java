package com.nevergetme.nevergetmeweb.mapper;

import com.nevergetme.nevergetmeweb.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    public List<User> findAll();
    public List<User> findUser(String username);
    public User findUserByUserId(Integer id);
    public User findUserByUserName(String username);
    public User findUserByPhone(String phone);
}

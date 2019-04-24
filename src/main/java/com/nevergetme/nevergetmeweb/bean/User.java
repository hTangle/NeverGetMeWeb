package com.nevergetme.nevergetmeweb.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String gender;
    private String trueName;
    private String birthday;
    private String email;
    private String personalBrief;
    private String avatarImgUrl;
    private String recentlyLanded;

//    private Date birthday;
//    private String sex;
//    private String address;

    public User(){}
    public User(int id,String username){
        this.id=id;
        this.username=username;
    }

    public User(Integer id,String username,String password){
        this.id=id;
        this.username=username;
        this.password=password;
    }
}

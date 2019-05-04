package com.nevergetme.nevergetmeweb.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String user_uuid;   //用户UUID
    private String username;    //用户名
    private String password;    //用户密码
    private String email;       //用户邮箱
    private String telephone;   //电话号码
    private String role;        //用户角色
    private String image;       //用户头像
    private String last_ip;     //上次登录IP
    private String last_time;   //上次登录时间
//    private Integer id;
//    private String username;
//    private String password;
//    private String phone;
//    private String gender;
//    private String trueName;
//    private String birthday;
//    private String email;
//    private String personalBrief;
//    private String avatarImgUrl;
//    private String recentlyLanded;

//    private Date birthday;
//    private String sex;
//    private String address;

    public User(){}
    public User(int id,String username){
        this.id=id;
        this.username=username;
    }
    public User(String user_uuid,String username,String password,String email,String image){
        this.user_uuid=user_uuid;
        this.username=username;
        this.password=password;
        this.email=email;
        this.image=image;
    }
    public User(int id,String user_uuid,String username,String password,String role,String image){
        this.id=id;
        this.user_uuid=user_uuid;
        this.username=username;
        this.password=password;
        this.role=role;
        this.image=image;
    }

    public User(Integer id,String username,String password){
        this.id=id;
        this.username=username;
        this.password=password;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user_uuid='" + user_uuid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", role='" + role + '\'' +
                ", image='" + image + '\'' +
                ", last_ip='" + last_ip + '\'' +
                ", last_time='" + last_time + '\'' +
                '}';
    }
}

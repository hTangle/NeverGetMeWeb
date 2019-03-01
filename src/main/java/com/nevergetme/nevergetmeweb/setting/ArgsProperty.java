package com.nevergetme.nevergetmeweb.setting;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ArgsProperty {
    Properties props=new Properties();
    boolean existState=false;
    String username,password,ID;
    public ArgsProperty(){
        try {
            props.load(new FileInputStream("./prop.ini"));
        } catch (IOException e) {
            existState=false;
            return;
            //e.printStackTrace();
        }

        username=getUsername();
        password=getPassword();
        ID=getID();
        if(username==null||password==null){

        }else{
            existState=true;
        }
    }
    public String getPassword(){
        return props.getProperty("password");
    }
    public String getUsername(){
        return props.getProperty("username");
    }
    public void setUsername(String username){
        props.setProperty("username",username);
        this.username=username;
    }
    public void setPassword(String password){
        props.setProperty("password",password);
        this.password=password;
    }
    public void setID(String ID){
        props.setProperty("ID",ID);
        this.ID=ID;
    }
    public String getID(){
        return props.getProperty("ID");
    }
    public String getCheck(){
        return props.getProperty("check");
    }
    public void saveProp(){
        try {
            props.store(new FileOutputStream("./prop.ini"),"user properity");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static String base64EncodeChars="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    public static String base64encode(String password){
        String out="";
        int i=0,len=password.length();
        int c1,c2,c3;
        while(i<len){
            c1=password.charAt(i++)&0xff;
            if(i==len){
                out += base64EncodeChars.charAt(c1 >> 2);
                out += base64EncodeChars.charAt((c1 & 0x3) << 4);
                out += "==";
                break;
            }
            c2=password.charAt(i++);
            if(i==len){
                out += base64EncodeChars.charAt(c1 >> 2);
                out += base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));
                out += base64EncodeChars.charAt((c2 & 0xF) << 2);
                out += "=";
                break;
            }
            c3=password.charAt(i++);
            out += base64EncodeChars.charAt(c1 >> 2);
            out += base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));
            out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >>6));
            out += base64EncodeChars.charAt(c3 & 0x3F);
        }
        return out;
    }
}

package com.nevergetme.nevergetmeweb.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@EnableAutoConfiguration
public class UserLogin {
    @CrossOrigin
    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    public @ResponseBody
    Map<String,String> getSensitiveWord(String userName,String password, HttpServletRequest request){
        Map<String,String> map=new HashMap<>();
        if(userName.equals("admin@seu.com")&&password.equals("admin")){
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", "20190302");
            map.put("state","1");
        }else{
            map.put("state","0");
        }
        return map;
//
//
//        String output=filter.replaceSensitiveWord(words,1,"*");
//        //System.out.println(words+":"+output);
//        map.put("output",output);
//        map.put("input",words);
//        map.put("ip",request.getRemoteAddr());
//        return map;
    }
}

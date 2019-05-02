package com.nevergetme.nevergetmeweb.restcontroller;

import com.nevergetme.nevergetmeweb.bean.User;
import com.nevergetme.nevergetmeweb.config.StaticConfigParam;
import com.nevergetme.nevergetmeweb.service.UserService;
import com.nevergetme.nevergetmeweb.utility.ContentUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@EnableAutoConfiguration
public class UserRestController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public @ResponseBody
    String createUser(
            @RequestParam(value = "id", required = true) int id,
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "role", required = true) String role,
            @RequestParam(value = "image", required = true) String image,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        User user = null;
        try {
            user = new User(id, ContentUtility.getRandomString(32), username, ContentUtility.encodeByMd5(password), role, image);
            userService.createUser(user);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "create user";
    }
    @RequestMapping(value = "/getCurrentUser")
    public @ResponseBody User getCurrentUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute(StaticConfigParam.LOGIN_IN_USER_ID)!=null) {
            int id = (Integer) session.getAttribute(StaticConfigParam.LOGIN_IN_USER_ID);
            return userService.findUserByUserId(id);
        }
        return new User();
    }

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    public @ResponseBody
    Map<String,String> userLoginIn(String userName, String password, HttpServletRequest request){
        Map<String,String> map=new HashMap<>();
        if(!userName.equals("")&&!password.equals("")){
            User user = userService.findUserByUserNameAndPassword(userName,password);
            if(user!=null){
                HttpSession session=request.getSession();
                session.setAttribute(StaticConfigParam.LOGIN_IN_USER_ID,user.getId());
                map.put("state","1");
            }else{
                map.put("state","0");
            }
        }else {
            map.put("state","-1");
        }
        return map;
    }
    @RequestMapping(value = "/user/logout",method = RequestMethod.POST)
    public @ResponseBody Map<String,String> userLogOut(HttpServletRequest request){
        Map<String,String> map=new HashMap<>();
        HttpSession session=request.getSession();
        if(session.getAttribute(StaticConfigParam.LOGIN_IN_USER_ID)!=null)
            session.removeAttribute(StaticConfigParam.LOGIN_IN_USER_ID);
        map.put("state","1");
        return map;
    }
}

package com.nevergetme.nevergetmeweb.restcontroller;

import com.nevergetme.nevergetmeweb.bean.User;
import com.nevergetme.nevergetmeweb.config.StaticConfigParam;
import com.nevergetme.nevergetmeweb.service.SendEmailService;
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
import java.util.UUID;

@RestController
@EnableAutoConfiguration
public class UserRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private SendEmailService sendEmailService;

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,String> createUser(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "authCode", required = true) int authCode,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String,String> map=new HashMap<>();
        int sendAuthCode=sendEmailService.getRegisterAuthCode(email);
        if(sendAuthCode!=0&&sendAuthCode==authCode){
            User user = null;
            try {
                user=new User(UUID.randomUUID().toString().replace("-",""),username,ContentUtility.encodeByMd5(password),email,StaticConfigParam.DEFAULT_USER_IMAGE);
                userService.createUser(user);
                sendEmailService.deleteRegisterAuthCode(email);
                map.put("state","1");
            } catch (NoSuchAlgorithmException e) {
                map.put("state","0");
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                map.put("state","0");
                e.printStackTrace();
            }
        }else{
            map.put("state","-1");
            map.put("message","验证码不正确");
        }
        return map;
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
    Map<String,String> userLoginIn(String email, String password, HttpServletRequest request){
        Map<String,String> map=new HashMap<>();
        if(!email.equals("")&&!password.equals("")){
            User user = userService.findUserByUserEmailAndPassword(email,password);
            if(user!=null){
                HttpSession session=request.getSession();
                session.setAttribute(StaticConfigParam.LOGIN_IN_USER_ID,user.getId());
                session.setAttribute(StaticConfigParam.LOGIN_USER,user);
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
        if(session.getAttribute(StaticConfigParam.LOGIN_IN_USER_ID)!=null) {
            session.removeAttribute(StaticConfigParam.LOGIN_IN_USER_ID);
            session.removeAttribute(StaticConfigParam.LOGIN_USER);
        }
        map.put("state","1");
        return map;
    }

    @RequestMapping(value = "/user/sendEmail",method = RequestMethod.POST)
    public @ResponseBody Map<String,String> sendEmail(
            @RequestParam(value = "to", required = true) String to,
            @RequestParam(value = "subject", required = true) String subject,
            @RequestParam(value = "content", required = true) String content
    ){
        Map<String,String> map=new HashMap<>();
        sendEmailService.sendSimpleEmail(to,subject,content);
        map.put("state","1");
        return map;
    }

    @RequestMapping(value = "/user/getAuthCode",method = RequestMethod.POST)
    public @ResponseBody Map<String,String> getAuthCode(@RequestParam(value = "to", required = true) String to){
        Map<String,String> map=new HashMap<>();
        User user=userService.findUserByEmail(to);
        if(user==null){
            if (sendEmailService.sendRegisterEmail(to)) {
                map.put("state", "1");
            } else {
                map.put("state", "0");
            }
        }else{
            map.put("state","-1");
            map.put("message","你已经注册过了!");
        }
        return map;
    }


}

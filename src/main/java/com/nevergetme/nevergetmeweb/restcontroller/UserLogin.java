package com.nevergetme.nevergetmeweb.restcontroller;

import com.nevergetme.nevergetmeweb.bean.Person;
import com.nevergetme.nevergetmeweb.bean.User;
import com.nevergetme.nevergetmeweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@EnableAutoConfiguration
public class UserLogin {
    @Autowired
    private UserService userService;
    @RequestMapping("/user/list")
    public List<User> list(){
        List<User> list = userService.findAllUser();
        return list;
    }
    @CrossOrigin
    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    public @ResponseBody
    Map<String,String> userLoginIn(String userName,String password, HttpServletRequest request){
        Map<String,String> map=new HashMap<>();
        if(!userName.equals("")&&!password.equals("")){
            List<User> list = userService.findUser(userName);
            if(list.size()==1){
                if(list.get(0).getPassword().equals(password)){
                    HttpSession session = request.getSession(true);
                    session.setAttribute("userid", list.get(0).getId());
                    map.put("state","1");
                }
            }else{
                map.put("state","0");
            }
        }else {
            map.put("state","-1");
        }
        return map;
    }
}

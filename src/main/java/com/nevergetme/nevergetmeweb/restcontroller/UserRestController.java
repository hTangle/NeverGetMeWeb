package com.nevergetme.nevergetmeweb.restcontroller;

import com.nevergetme.nevergetmeweb.bean.User;
import com.nevergetme.nevergetmeweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@EnableAutoConfiguration
public class UserRestController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/user/user",method = RequestMethod.GET)
    public @ResponseBody
    User findUserByUserId(String id, HttpServletRequest request){
        return userService.findUserByUserId(Integer.parseInt(id));
    }
    @RequestMapping(value = "/user/getUserInformation",method = RequestMethod.POST)
    public @ResponseBody
    User getUserInformation(HttpServletRequest request){
        //到这说明用户已经登录了
        HttpSession session = request.getSession(true);
        int id=(Integer)session.getAttribute("id");
        return userService.findUserByUserId(id);
    }
}

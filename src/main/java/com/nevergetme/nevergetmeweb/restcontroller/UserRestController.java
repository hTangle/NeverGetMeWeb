package com.nevergetme.nevergetmeweb.restcontroller;

import com.nevergetme.nevergetmeweb.bean.User;
import com.nevergetme.nevergetmeweb.service.UserService;
import com.nevergetme.nevergetmeweb.utility.ContentUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@EnableAutoConfiguration
public class UserRestController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/user", method = RequestMethod.GET)
    public @ResponseBody
    User findUserByUserId(String id, HttpServletRequest request) {
        return userService.findUserByUserId(Integer.parseInt(id));
    }

    @RequestMapping(value = "/user/getUserInformation", method = RequestMethod.POST)
    public @ResponseBody
    User getUserInformation(HttpServletRequest request) {
        //到这说明用户已经登录了
        HttpSession session = request.getSession(true);
        int id = (Integer) session.getAttribute("id");
        return userService.findUserByUserId(id);
    }

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
        Md4PasswordEncoder encoder = new Md4PasswordEncoder();
        User user = new User(id, ContentUtility.getRandomString(32), username, encoder.encode(password), role, image);
        userService.createUser(user);
        return "create user";
    }
    @RequestMapping(value = "/getCurrentUser")
    public @ResponseBody User getCurrentUser(){
        return ContentUtility.getUser();
    }
}

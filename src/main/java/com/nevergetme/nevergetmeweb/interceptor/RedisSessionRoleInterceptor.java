package com.nevergetme.nevergetmeweb.interceptor;

import com.nevergetme.nevergetmeweb.bean.User;
import com.nevergetme.nevergetmeweb.config.StaticConfigParam;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Create by Alden He on 2019/5/12
 * 这个拦截器控制的是admin的相关权限
 * 如果没有admin权限（默认为10）则拒绝访问
 */
@Component
public class RedisSessionRoleInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception{
        HttpSession session=request.getSession();
        User user;
        if((user=(User)session.getAttribute(StaticConfigParam.LOGIN_USER))!=null&&StaticConfigParam.ADMIN_ROLE.equals(user.getRole())){
            return true;
        }
        response.sendRedirect("/404");
        return false;
    }
}

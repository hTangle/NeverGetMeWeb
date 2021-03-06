package com.nevergetme.nevergetmeweb.interceptor;

import com.nevergetme.nevergetmeweb.config.StaticConfigParam;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 这个拦截器主要控制需要登录才能进行的工作
 */
@Component
public class RedisSessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)throws Exception{
        HttpSession session=request.getSession();
        if(session.getAttribute(StaticConfigParam.LOGIN_IN_USER_ID)!=null){
            return true;
        }
        response.sendRedirect("/404");
        return false;
    }
}

package com.nevergetme.nevergetmeweb.interceptor;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Component
public class MyInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // TODO Auto-generated method stub
        //获取session
        logger.info("---------------------开始进入请求地址拦截----------------------------");
        //return true;
        HttpSession session = request.getSession(true);
        if (session.getAttribute("userId") == null) {
            logger.info("------:跳转到login页面！");
//            response.reset();

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;

            JSONObject res = new JSONObject();
            res.put("state", "0");
            res.put("msg", "login in first");
            res.put("output","");
            out = response.getWriter();
            out.append(res.toString());
            return false;
        } else {
            session.setAttribute("userId", session.getAttribute("userId"));
            return true;
        }
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        logger.info("--------------处理请求完成后视图渲染之前的处理操作---------------");
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
        logger.info("---------------视图渲染之后的操作-------------------------0");
    }
}

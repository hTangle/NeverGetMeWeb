package com.nevergetme.nevergetmeweb.config;

import com.nevergetme.nevergetmeweb.interceptor.RedisSessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class RedisWebSecurityConfig implements WebMvcConfigurer {
    @Resource
    private RedisSessionInterceptor interceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).
                addPathPatterns("/editArticle").
                excludePathPatterns("/createUser").
                addPathPatterns("/comments/addComments").
                excludePathPatterns("/comments/getComments").
                addPathPatterns("/comments/addReplyComment").
                addPathPatterns("/user/logout").
                addPathPatterns("/article/createArticle").
                addPathPatterns("/article/uploadImage").
                addPathPatterns("/admin").
                addPathPatterns("/admin/*").
                excludePathPatterns("/user/login").
                excludePathPatterns("/user/getAuthCode").
                excludePathPatterns("/").
                excludePathPatterns("/404").
                excludePathPatterns("/article/*").
                excludePathPatterns("/article/showArticle/*").
                excludePathPatterns("/icon/**").
                excludePathPatterns("/js/**").
                excludePathPatterns("/css/**").
                excludePathPatterns("/plug/**").
                excludePathPatterns("/source/**").
                excludePathPatterns("/bootstrap/**").
                excludePathPatterns("/getCurrentUser")
        ;//.excludePathPatterns("/login.html").excludePathPatterns("/*.min.js.*");
    }
    //                antMatchers("/").permitAll().
//                antMatchers("/404").permitAll().
//                antMatchers("/article/*").permitAll().
//                antMatchers("/article/showArticle/*").permitAll().
//                antMatchers("/icon/**").permitAll().
//                antMatchers("/js/**").permitAll().
//                antMatchers("/css/**").permitAll().
//                antMatchers("/plug/**").permitAll().
//                antMatchers("/source/**").permitAll().
//                antMatchers("/bootstrap/**").permitAll().
////                antMatchers("/createUser").permitAll().
//                antMatchers("/getCurrentUser").permitAll().
//                antMatchers("/editArticle").permitAll().anyRequest().authenticated().
}

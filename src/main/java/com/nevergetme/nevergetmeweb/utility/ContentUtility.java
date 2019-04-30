package com.nevergetme.nevergetmeweb.utility;

import com.nevergetme.nevergetmeweb.bean.User;
import org.jsoup.Jsoup;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ContentUtility {
    private static final int SHORT_CUT_LINE=2;
    public static User getUser() { //为了session从获取用户信息,可以配置如下
        User user = new User();
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) user = (User) auth.getPrincipal();
        return user;
    }
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();
        ThreadLocalRandom uuidRandom=ThreadLocalRandom.current();
        for (int i = 0; i < length; ++i) {
            sb.append(str.charAt(uuidRandom.nextInt(62)));
        }
        return sb.toString();
    }
    public static String getArticleShortCut(String articleContent){
        String s=Jsoup.parse(articleContent).text();
        return s.substring(0,100)+"...";
//        int index=0;
//        int count=0;
//        while (count<SHORT_CUT_LINE){
//            int k=articleContent.indexOf("\n",index);
//            if(k!=-1){
//                k=index;
//                count++;
//            }else{
//                break;
//            }
//        }
//        return articleContent.substring(index);
    }
}

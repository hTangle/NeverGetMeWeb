package com.nevergetme.nevergetmeweb.utility;

import com.nevergetme.nevergetmeweb.bean.User;
import com.nevergetme.nevergetmeweb.config.StaticConfigParam;
import org.jsoup.Jsoup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentUtility {
    private static final int SHORT_CUT_LINE=2;
    private static final Pattern PATTERN_COVER=Pattern.compile("\\]\\(/[0-9]{13,14}_[0-9]{5}.png\\)");
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        System.out.println(encodeByMd5("he187127..."));
    }
    public static String encodeByMd5(String string) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        Base64.Encoder base64Encoder = Base64.getEncoder();
        // 加密字符串
        return base64Encoder.encodeToString(md5.digest(string.getBytes("utf-8")));
    }
    public static User getUser() { //为了session从获取用户信息,可以配置如下
        User user = new User();
//        SecurityContext ctx = SecurityContextHolder.getContext();
//        Authentication auth = ctx.getAuthentication();
//        if (auth.getPrincipal() instanceof UserDetails) user = (User) auth.getPrincipal();
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
    public static int getRandomInteger(){
        ThreadLocalRandom authCodeRandom=ThreadLocalRandom.current();
        return authCodeRandom.nextInt(StaticConfigParam.EMAIL_CODE_BEGIN,StaticConfigParam.EMAIL_CODE_MAXIMUM);
    }
    public static String getArticleShortCut(String articleContent){
        String s=Jsoup.parse(articleContent).text();
        if(s.length()<100)return s;
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
    public static int getCurrentUserId(HttpServletRequest request){
        HttpSession session=request.getSession();
        if(session.getAttribute(StaticConfigParam.LOGIN_IN_USER_ID)!=null){
            return (Integer)session.getAttribute(StaticConfigParam.LOGIN_IN_USER_ID);
        }else{
            return -1;
        }
    }
    public static User getCurrentUser(HttpServletRequest request){
        HttpSession session=request.getSession();
        if(session.getAttribute(StaticConfigParam.LOGIN_USER)!=null){
            return (User)session.getAttribute(StaticConfigParam.LOGIN_USER);
        }else {
            return null;
        }
    }
     /**
     　　* @description: TODO 查找content中符合![**](*.png)的内容
     　　* @param
     　　* @return 
     　　* @throws 
     　　* @author Alden He
     　　* @date 2019/5/15 19:36 
     　　*/
    public static String getCoverFromContent(String content){
        Matcher m=PATTERN_COVER.matcher(content);
        if(m.find()){
            String cover=m.group(0);
            return cover.substring(2,cover.length()-1);
        }
        return null;
    }
}

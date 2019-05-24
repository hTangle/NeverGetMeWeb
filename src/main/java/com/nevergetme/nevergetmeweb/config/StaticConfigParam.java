package com.nevergetme.nevergetmeweb.config;

public class StaticConfigParam {
    public static final int USER_ID_BEGIN=10000;
    public static final int ARTICLE_ID_BEGIN=100000;
    public static final int COMMENT_MAX_LENGTH=100;
    //redis存储的key首
    public static final String ARTICLE_LIST_TAG_ID="article_list_tags_";
    public static final String LOGIN_IN_USER="login_user_";
    public static final String LOGIN_IN_USER_ID="loginInUserID";
    public static final String LOGIN_USER="LoginUser";
    public static final String TAGS_LIST="tags_list";
    public static final long EXIPRE_TIME=10*24*60*60;
    public static final long EXPIRE_TIME_HOUR=10*24;
    public static final int PAGE_SIZE=10;
    public static final String ARTICLE_LIST_KEY = "article_list_";
    public static final String SEARCH_HISTROY_LIST="search_history_list";

    //发送email相关信息
    public static final String REGISTER_SEND_EMAIL_SUBJECT="Register email";
    public static final int EMAIL_CODE_MAXIMUM=899999;
    public static final int EMAIL_CODE_BEGIN=100000;
    public static final String REGISTER_SEND_EMAIL_CONTENT="您刚刚通过邮箱注册nevergetme,您的验证码为:";
    public static final String REGISTER_EMAIL_CODE_REDIS="send_email_";
    public static final String DEFAULT_USER_ROLE="0";
    public static final String DEFAULT_USER_IMAGE="/icon/user.svg";

    public static final String ADMIN_ROLE="10";
}

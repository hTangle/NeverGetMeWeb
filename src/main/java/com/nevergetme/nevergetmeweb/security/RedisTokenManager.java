package com.nevergetme.nevergetmeweb.security;

import com.nevergetme.nevergetmeweb.bean.User;

public class RedisTokenManager implements TokenManager{

    @Override
    public String getToken(User user) {

        return null;
    }

    @Override
    public void refreshUserToken(String token) {

    }

    @Override
    public void loginOff(String token) {

    }

    @Override
    public User getUserInfoByToken(String token) {
        return null;
    }
}

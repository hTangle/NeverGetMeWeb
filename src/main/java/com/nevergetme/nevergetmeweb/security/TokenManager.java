package com.nevergetme.nevergetmeweb.security;

import com.nevergetme.nevergetmeweb.bean.User;

public interface TokenManager {
    String getToken(User user);
    void refreshUserToken(String token);
    void loginOff(String token);
    User getUserInfoByToken(String token);
}

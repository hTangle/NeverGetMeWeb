package com.nevergetme.nevergetmeweb.service;

public interface SendEmailService {
    void sendSimpleEmail(String to,String subject,String content);
    boolean sendRegisterEmail(String to);
    int getRegisterAuthCode(String to);
    boolean deleteRegisterAuthCode(String to);
    void sendHtmlEmail(String to,String subject,String content);
}

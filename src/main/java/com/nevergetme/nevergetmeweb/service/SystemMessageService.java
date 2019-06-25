package com.nevergetme.nevergetmeweb.service;

import com.nevergetme.nevergetmeweb.bean.SystemMessage;

import java.util.List;

/**
 * Create by Alden He on 2019/6/1
 */
public interface SystemMessageService {
    void addSystemMessage(SystemMessage message);
    void updateSystemMessageReadState(int id);
    void updateSystemMessageSendState(int id);
    List<SystemMessage> readSystemMessage(int receiver);
}

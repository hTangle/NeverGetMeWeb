package com.nevergetme.nevergetmeweb.service.impl;

import com.nevergetme.nevergetmeweb.bean.SystemMessage;
import com.nevergetme.nevergetmeweb.mapper.SystemMessageMapper;
import com.nevergetme.nevergetmeweb.service.SystemMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by Alden He on 2019/6/1
 */
@Service
public class SystemMessageServiceImpl implements SystemMessageService {
    @Autowired
    private SystemMessageMapper messageMapper;
    @Override
    public void addSystemMessage(SystemMessage message) {
        messageMapper.addSystemMessage(message);
    }

    @Override
    public void updateSystemMessageReadState(int id) {
        messageMapper.updateSystemMessageReadState(id);
    }

    @Override
    public void updateSystemMessageSendState(int id) {
        messageMapper.updateSystemMessageSendState(id);
    }

    @Override
    public List<SystemMessage> readSystemMessage(int receiver) {
        return messageMapper.readSystemMessage(receiver);
    }
}

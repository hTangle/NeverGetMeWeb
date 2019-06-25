package com.nevergetme.nevergetmeweb.mapper;

import com.nevergetme.nevergetmeweb.bean.SystemMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Create by Alden He on 2019/6/1
 */

@Mapper
public interface SystemMessageMapper {
    void addSystemMessage(SystemMessage message);
    void updateSystemMessageReadState(int id);
    void updateSystemMessageSendState(int id);
    List<SystemMessage> readSystemMessage(int receiver);
}

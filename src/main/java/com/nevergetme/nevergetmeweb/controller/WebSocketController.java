package com.nevergetme.nevergetmeweb.controller;

import com.nevergetme.nevergetmeweb.bean.SystemMessage;
import com.nevergetme.nevergetmeweb.bean.User;
import com.nevergetme.nevergetmeweb.config.WebSocketConfig;
import com.nevergetme.nevergetmeweb.service.SystemMessageService;
import com.nevergetme.nevergetmeweb.utility.ContentUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Create by Alden He on 2019/5/31
 */
@ServerEndpoint(value = "/websocket",configurator = WebSocketConfig.class)
@Component
public class WebSocketController {
//    @Autowired
//    private SystemMessageService systemMessageService;

    private static int onlineCount=0;
    private static CopyOnWriteArraySet<WebSocketController> webSocketControllers=new CopyOnWriteArraySet<>();

    private Session session;
    private HttpSession httpSession;
    private boolean isSuccess=false;
    private int userId;


    private boolean hasAlreadyInArraySet(User user){
        for(WebSocketController w:webSocketControllers){
            if(w.userId==user.getId()){
                return true;
            }
        }
        return false;
    }

    @OnOpen
     /**
     　　* @description: 目前是如果登录的情况下，每次打开一个页面，就会将这个socket添加到Set中
     　　* @param [session, config]
     　　* @return void
     　　* @throws
     　　* @author Alden He
     　　* @date 2019/6/1 18:31
     　　*/
    public void onOpen(Session session, EndpointConfig config) throws IOException {
        HttpSession httpSession=(HttpSession)config.getUserProperties().get(HttpSession.class.getName());
        User user;
        if((user= ContentUtility.getCurrentUserBySession(httpSession))!=null&&!hasAlreadyInArraySet(user)){
            this.session=session;
            this.httpSession=httpSession;
            isSuccess=true;
            userId=user.getId();
            webSocketControllers.add(this);
            addOnlineCount();
            System.out.println("目前在线人数为:"+getOnlineCount());
//            sendMessage(new SystemMessage("目前在线人数为:"+getOnlineCount()));
        }else{
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @OnClose
    public void onClose(){
        webSocketControllers.remove(this);
        subOnlineCount();
        System.out.println("离线，目前在线人数为:"+getOnlineCount());
    }
    @OnMessage
    public void onMessage(String message, Session session)throws IOException {
        System.out.println("来自客户端的消息："+message);
    }
    public void sendMessage(SystemMessage systemMessage)throws IOException{
        this.session.getBasicRemote().sendText(systemMessage.getContent());
        systemMessage.setSendState(1);
//        systemMessageService.addSystemMessage(systemMessage);
    }

    public synchronized void addOnlineCount(){
        WebSocketController.onlineCount++;
    }
    public synchronized int getOnlineCount(){
        return WebSocketController.onlineCount;
    }
    public synchronized void subOnlineCount(){
        if(isSuccess)
            WebSocketController.onlineCount--;
    }
    public static boolean sendMessageToUser(SystemMessage systemMessage){
        System.out.println("Begin to Send Info:"+systemMessage.getContent());
        for(WebSocketController w:webSocketControllers){
            if(systemMessage.getReceiver()==w.userId){
                try {
                    if(w.isSuccess){
                        w.sendMessage(systemMessage);
                        return true;
                        //systemMessage.setSendState(1);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return false;
    }

}

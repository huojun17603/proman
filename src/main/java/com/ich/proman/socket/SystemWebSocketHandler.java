package com.ich.proman.socket;

import com.ich.proman.base.Constant;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;

public class SystemWebSocketHandler implements WebSocketHandler {

    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();

    /**
     * 建立连接时进入此方法
     *
     * */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        System.out.println("ConnectionEstablished");
        String userName = session.getAttributes().get(Constant.WEBSOCKET_USERNAME).toString();
        System.out.println();
        users.add(session);

        String names = "";
        //提示其他在线用户你上线了 非必须代码
        for (WebSocketSession user : users) {
            if(session!=user) {//登录提示
                user.sendMessage(new TextMessage(forMatterXML(userName,"上线了!", 0)));
            } else {
                for(int i=0;i<users.size();i++) {//每次登陆都将在线的用户进行遍历组装，并发送给自己 当中联系人列表
                    if(users.size()-1==i) {//最后一条
                        names += users.get(i).getAttributes().get(Constant.WEBSOCKET_USERNAME).toString();
                    } else {
                        names += users.get(i).getAttributes().get(Constant.WEBSOCKET_USERNAME).toString()+",";
                    }
                }
                user.sendMessage(new TextMessage(forMatterXML(userName,names, 4)));
            }
        }
        //欢迎语
        session.sendMessage(new TextMessage(forMatterXML(userName,"欢迎来到小陈聊天室!", 1)));
    }

    /**
     * 客户端发送消息时进入此方法
     *
     * */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        sendMessageToUsers(new TextMessage(message.getPayload().toString()),session);
    }

    /**
     * 异常进入此方法
     *
     * */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        users.remove(session);
    }

    /**
     * 关闭进入此方法
     *
     * */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String userName = session.getAttributes().get(Constant.WEBSOCKET_USERNAME).toString();
        System.out.println(userName+"离开了");
        for (WebSocketSession user : users) {
            if(session!=user) {
                user.sendMessage(new TextMessage(forMatterXML(userName,"离线了!",5)));
            }
        }
        users.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 在线聊天
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message,WebSocketSession session) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    String userName = session.getAttributes().get(Constant.WEBSOCKET_USERNAME).toString();
                    user.sendMessage(new TextMessage(forMatterXML(userName, message.getPayload(),2)));
                    //user.sendMessage(new TextMessage("<a href='javascript:onclick=sl(\""+userName+"\")'>"+userName+"</a>:<font color='blue'>"+message.getPayload()+"</font></br>"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 一对一聊天
     *
     * @param userName1,userName2
     * @param message
     */
    public void sendMessageToUser(String userName1,String userName2,TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.getAttributes().get(Constant.WEBSOCKET_USERNAME).equals(userName1)||user.getAttributes().get(Constant.WEBSOCKET_USERNAME).equals(userName2)) {
                    if (user.isOpen()) {
                        //String userName = user.getAttributes().get(Constants.WEBSOCKET_USERNAME).toString();
                        user.sendMessage(new TextMessage(forMatterXML(userName1, message.getPayload(),3)));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * 解析XML
     * @param userName
     * @param message
     * @param type
     * @return
     */
    private String forMatterXML(String userName,String message,Integer type) {
        String XML =
                "<persons>"
                        + "<person>"
                        + "<name>"+userName+"</name>"
                        + "<message>"+message+"</message>"
                        + "<type>"+type+"</type>"
                        +   "</person>"
                        +"</persons>";

        return XML;
    }
}

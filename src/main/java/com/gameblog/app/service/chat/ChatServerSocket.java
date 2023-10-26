/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.service.chat;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author orlan
 */

@ServerEndpoint("/chat")
public class ChatServerSocket {
    
    private static final Logger logger = Logger.getLogger(ChatServerSocket.class.getName());
    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();
    
    
    @OnOpen
    public void onOpen(Session session){
        sessions.add(session);
    }
    
    @OnMessage
        public void onMessage(String message){
        if(message==null || message.isEmpty())
            throw  new RuntimeException("No atual message received");
        broadcastMessage(message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason){
        sessions.remove(session);
    }
    
    @OnError
    public void onError(Throwable error){
        logger.log(Level.INFO,"WebSocket server error",error.getMessage());
    }
    
    public void broadcastMessage(String message){
        sessions.stream().forEach(s -> s.getAsyncRemote().sendText(message));
    }
    
    
}

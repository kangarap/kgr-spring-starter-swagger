package com.kgr.framework.websocket.core;

import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyWebSocketHandler implements WebSocketHandler {
    private static final Map<String, WebSocketSession> SESSIONS = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String sessionId = session.getAttributes().get("sessionId").toString();
        SESSIONS.put(sessionId, session);

        System.out.printf("成功建立连接~ sessionId: %s%n", session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String msg = message.getPayload().toString();
        //收到消息
        System.out.println(msg);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("连接出错");
        if (session.isOpen()) {
            session.close();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("连接已关闭,status:" + closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 指定发消息
     */
    public static void sendMessage(String userName, String message) {
        WebSocketSession webSocketSession = SESSIONS.get(userName);
        if (webSocketSession == null || !webSocketSession.isOpen()) return;
        try {
            webSocketSession.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
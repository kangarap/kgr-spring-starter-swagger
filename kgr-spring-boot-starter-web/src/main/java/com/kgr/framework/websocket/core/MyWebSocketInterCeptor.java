package com.kgr.framework.websocket.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 自定义拦截器拦截WebSocket请求
 */
@Configuration
public class MyWebSocketInterCeptor implements HandshakeInterceptor {
    //前置拦截一般用来注册用户信息，绑定 WebSocketSession
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        System.out.println("前置拦截~~");

        if (!(request instanceof ServletServerHttpRequest)) return true;

        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        String sessionId = (String) servletRequest.getSession().getAttribute("sessionId");
        attributes.put("sessionId", sessionId);
        return true;
    }
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        System.out.println("后置拦截~~");
    }
}
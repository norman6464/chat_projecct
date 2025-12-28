package com.example.chat.websocket;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.example.chat.chat.ChatMessage;
import com.example.chat.chat.MessageType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConnectionEventListener {
  
  private final SimpMessageSendingOperations messageTemplate;
  
  
  // イベントアノテーションをつけたことによりdisconnectイベントが発生したときにイベントオブジェクトを返す
  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    // セッション属性 → WebSocket接続から切断までの変数、属性
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage()); 
    String username = (String) headerAccessor.getSessionAttributes().get("username");
    if (username != null) {
      ChatMessage chatMessage = ChatMessage.builder()
        .type(MessageType.LEAVE)
        .sender(username)
        .build();
        messageTemplate.convertAndSend("/topic/public", chatMessage);
    }
  }
}

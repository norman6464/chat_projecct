package com.example.chat.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class ConnectionConfig implements WebSocketMessageBrokerConfigurer {
  
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws").withSockJS();
    // WebSocketの通信が失敗してもSockJSが複数のルートを用意して通信してくれるので通信が安定になる
    // 接続方法
    // 1. WebSocket（そのまま繋がればWebSocketで通信をする）
    // 2. XHR Streaming
    // 3. XHR Polling
    // 4. Long Polling
    // この2 ~ 4でHTTPベースの疑似リアルタイム通信に切り替える
  }
  
  // メッセージブローカーの設定であり「メッセージの行き先をどう分けるか」を決める
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    
    // サーバー側（Controller）が処理する宛先のプレフィックス
    // クライアントがここに送ると
    // @MessageMappingが付いたメソッドに届く
    registry.setApplicationDestinationPrefixes("/app");
    
    
    // Spring内臓の簡易メッセージブローカーを有効化
    // topic宛てのメッセージは
    // ・Controllerに通らず
    // ・ブローカーが直接購読者へ配信）
    // ・ブロードキャスト配信される
    registry.enableSimpleBroker("/topic"); 
  }
  
}

// [ブラウザ]
//    |
//    | ① SockJS + STOMP 接続
//    v
// /ws
//    |
//    | ② メッセージ送信
//    |    /app/xxx
//    v
// @Controller (@MessageMapping)
//    |
//    | ③ 配信
//    |    /topic/xxx
//    v
// [購読中のクライアント]


// | 設定                                        | 役割                     |
// | ----------------------------------------- | ---------------------- |
// | @EnableWebSocketMessageBroker             | WebSocket + STOMP を有効化 |
// | addEndpoint("/ws")                        | 接続URL                  |
// | withSockJS()                              | フォールバック付き通信            |
// | setApplicationDestinationPrefixes("/app") | Controller行き           |
// | enableSimpleBroker("/topic")              | クライアント配信               |

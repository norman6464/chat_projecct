package com.example.chat.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
  
  // registry.setApplicationDestinationPrefixesで設定したパスにクライアントがメッセージを送信すると
  // MessageMappingに紐づけられているメソッドに処理が行き届く
  // SendToアノテーションは戻り値をトピックに取得をしている
  @MessageMapping("/chat.sendMessage")
  @SendTo("/topic/public") // ここでトピックに指定をしてメッセージブローカーにメッセージを格納をする
  public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
    return chatMessage;
  }
  
  // 入室したユーザーがいたら知らせるメソッド
  @MessageMapping("/chat.addUser")
  @SendTo("/topic/public")
  // StompHeaderAccessorはイベントリスナークラスように使う（disconnect時に使用をする）
  public ChatMessage addUser(@Payload ChatMessage chatMessage, StompHeaderAccessor headerAccessor) {
    // 取得したユーザー名をトピックに登録する
    headerAccessor.getSessionAttributes().put("username", chatMessage.getSender()); // ここではキーバリューを設定をする
    return chatMessage;
  }
  
}

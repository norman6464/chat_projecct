package com.example.chat.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
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
  public ChatMessage addUser(@Payload ChatMessage chatMessage) {
    // 取得したユーザー名をトピックに登録する
    return chatMessage;
  }
  
}

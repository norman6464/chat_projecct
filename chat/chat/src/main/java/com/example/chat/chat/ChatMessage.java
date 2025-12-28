package com.example.chat.chat;

import lombok.Data;

// メッセージフォーマットとして機能するクラス（イメージDTOに近い）
@Data
public class ChatMessage {
  private MessageType type;
  private String content;
  private String sender;
}

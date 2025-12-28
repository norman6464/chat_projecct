package com.example.chat.chat;

import lombok.Builder;
import lombok.Data;

// メッセージフォーマットとして機能するクラス（イメージDTOに近い）
@Data
@Builder // 自動的にビルダーメソッドが追加される
public class ChatMessage {
  private MessageType type;
  private String content;
  private String sender;
}

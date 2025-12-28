package com.example.chat.chat;

import lombok.Data;

@Data
public class ChatMessage {
  private MessageType type;
  private String content;
  private String sender;
}

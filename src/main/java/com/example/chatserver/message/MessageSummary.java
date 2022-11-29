package com.example.chatserver.message;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MessageSummary {
    private final Long id;
    private final Long senderId;
    private final Long receiverId;
    private final LocalDateTime timestamp;
    private final String content;
    private final Boolean isRead;

    public MessageSummary(Message message) {
        this.id = message.getId();
        this.senderId = message.getSender().getId();
        this.receiverId = message.getReceiver().getId();
        this.timestamp = message.getTimestamp();
        this.content = message.getContent();
        this.isRead = message.getIsRead();
    }
}

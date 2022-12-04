package com.example.chatserver.message;

public record MessageSendRequest(
        Long senderId,
        Long receiverId,
        String content
) {
}

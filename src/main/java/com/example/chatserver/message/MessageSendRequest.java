package com.example.chatserver.message;

public record MessageSendRequest(
        Long receiverId,
        String content
) {
}

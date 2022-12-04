package com.example.chatserver.message;

import java.time.LocalDateTime;

public record MessageSummary(
        Long id,
        Long senderId,
        Long receiverId,
        LocalDateTime timestamp,
        String content,
        Boolean isRead
) {
}

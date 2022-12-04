package com.example.chatserver.appuser;

public record AppUserSummary(
        Long id,
        String firstName,
        String lastName,
        String displayName
) {
}

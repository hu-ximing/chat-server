package com.example.chatserver.appuser;

public record AppUserDTO(
        Long id,
        String firstName,
        String lastName,
        String displayName
) {
}

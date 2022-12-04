package com.example.chatserver.registration;

public record RegistrationRequest(
        String username,
        String password,
        String firstName,
        String lastName,
        String displayName
) {
}

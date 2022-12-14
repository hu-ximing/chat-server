package com.example.chatserver.appuser.exception;

public class AppUserNotFoundException extends RuntimeException {
    public AppUserNotFoundException(Long id) {
        super("Could not find app-user with id " + id);
    }
}

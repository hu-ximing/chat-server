package com.example.chatserver.appuser.exception;

public class UsernameTakenException extends RuntimeException {
    public UsernameTakenException(String username) {
        super("Username or email taken " + username);
    }
}

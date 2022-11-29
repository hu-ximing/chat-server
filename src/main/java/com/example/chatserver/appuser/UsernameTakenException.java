package com.example.chatserver.appuser;

public class UsernameTakenException extends RuntimeException {
    public UsernameTakenException(String username) {
        super("Username or email taken " + username);
    }
}

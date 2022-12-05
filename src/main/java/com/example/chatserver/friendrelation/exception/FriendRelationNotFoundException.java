package com.example.chatserver.friendrelation.exception;

public class FriendRelationNotFoundException extends RuntimeException {
    public FriendRelationNotFoundException(Long userId1, Long userId2) {
        super("Could not find FriendRelation between user with id " +
                userId1 + " and " + userId2);
    }
}

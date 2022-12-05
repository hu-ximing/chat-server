package com.example.chatserver.friendrelation;

public record FriendRelationRequest(
        Long friendUserId,
        String selfIntroduction
) {
}

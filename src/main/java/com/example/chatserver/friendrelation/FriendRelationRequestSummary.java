package com.example.chatserver.friendrelation;

import com.example.chatserver.appuser.AppUserSummary;

public record FriendRelationRequestSummary(
        AppUserSummary appUserSummary,
        String selfIntroduction
) {
}

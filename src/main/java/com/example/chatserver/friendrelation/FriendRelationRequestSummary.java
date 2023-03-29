package com.example.chatserver.friendrelation;

import com.example.chatserver.appuser.AppUserDTO;

public record FriendRelationRequestSummary(
        AppUserDTO appUserDTO,
        String selfIntroduction
) {
}

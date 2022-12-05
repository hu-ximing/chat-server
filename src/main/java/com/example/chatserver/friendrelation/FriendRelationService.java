package com.example.chatserver.friendrelation;

import com.example.chatserver.appuser.AppUser;
import com.example.chatserver.appuser.AppUserService;
import com.example.chatserver.appuser.AppUserSummary;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FriendRelationService {

    private final FriendRelationRepository friendRelationRepository;
    private final AppUserService appUserService;

    public List<AppUserSummary> getFriends() {
        AppUser appUser = appUserService.getLoggedInAppUser();
        return friendRelationRepository
                .findByAppUserOrderByLatestInteractionTimeDesc(appUser)
                .stream()
                .map(fr -> new AppUserSummary(
                        fr.getFriend().getId(),
                        fr.getFriend().getFirstName(),
                        fr.getFriend().getLastName(),
                        fr.getFriend().getDisplayName()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addFriendWith(Long friendUserId) {
        AppUser appUser = appUserService.getLoggedInAppUser();
        AppUser friend = appUserService.getUserById(friendUserId);
        LocalDateTime now = LocalDateTime.now();

        friendRelationRepository.saveAll(List.of(
                new FriendRelation(appUser, friend, now),
                new FriendRelation(friend, appUser, now)
        ));
    }

    @Transactional
    public void addFriend(Long userId1, Long userId2) {
        AppUser user1 = appUserService.getUserById(userId1);
        AppUser user2 = appUserService.getUserById(userId2);
        LocalDateTime now = LocalDateTime.now();

        friendRelationRepository.saveAll(List.of(
                new FriendRelation(user1, user2, now),
                new FriendRelation(user2, user1, now)
        ));
    }

    public LocalDateTime getLatestInteractionTime(Long friendUserId) {
        AppUser appUser = appUserService.getLoggedInAppUser();
        AppUser friend = appUserService.getUserById(friendUserId);
        return friendRelationRepository
                .findByAppUserAndFriend(appUser, friend)
                .orElseThrow(() -> new RuntimeException("Friend relation not found"))
                .getLatestInteractionTime();
    }

    @Transactional
    public void updateLatestInteractionTime(AppUser user1, AppUser user2, LocalDateTime localDateTime) {
        friendRelationRepository
                .findByAppUserAndFriend(user1, user2)
                .orElseThrow()
                .setLatestInteractionTime(localDateTime);
        friendRelationRepository
                .findByAppUserAndFriend(user2, user1)
                .orElseThrow()
                .setLatestInteractionTime(localDateTime);
    }
}

package com.example.chatserver.friendrelation;

import com.example.chatserver.appuser.AppUser;
import com.example.chatserver.appuser.AppUserService;
import com.example.chatserver.appuser.AppUserSummary;
import com.example.chatserver.friendrelation.exception.FriendRelationNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FriendRelationService {

    private final FriendRelationRepository friendRelationRepository;
    private final AppUserService appUserService;

    public List<AppUserSummary> mapRelationsToSummaries(List<FriendRelation> relations) {
        return relations
                .stream()
                .map(fr -> new AppUserSummary(
                        fr.getFriend().getId(),
                        fr.getFriend().getFirstName(),
                        fr.getFriend().getLastName(),
                        fr.getFriend().getDisplayName()
                ))
                .collect(Collectors.toList());
    }

    // only return accepted friends
    public List<AppUserSummary> getFriends() {
        AppUser appUser = appUserService.getLoggedInAppUser();
        List<FriendRelation> relations = friendRelationRepository
                .findByAppUserAndAcceptedTrueOrderByLatestInteractionTimeDesc(appUser);
        return mapRelationsToSummaries(relations);
    }

    public List<AppUserSummary> getReceivedFriendRequests() {
        AppUser appUser = appUserService.getLoggedInAppUser();
        List<FriendRelation> relations = friendRelationRepository
                .findReceivedFriendRequests(appUser);
        return mapRelationsToSummaries(relations);
    }

    @Transactional
    public void sendFriendRequest(FriendRelationRequest request) {
        AppUser appUser = appUserService.getLoggedInAppUser();
        AppUser friend = appUserService.getUserById(request.friendUserId());
        LocalDateTime now = LocalDateTime.now();

        Optional<FriendRelation> friendRelationOptional = friendRelationRepository
                .findByAppUserAndFriend(appUser, friend);

        // If a friend request is sent,
        // update self introduction and set rejected to false
        if (friendRelationOptional.isPresent()) {
            FriendRelation relation = friendRelationOptional.get();
            relation.setSelfIntroduction(request.selfIntroduction());
            relation.setRejected(false);
            relation.setLatestInteractionTime(now);
        } else {
            friendRelationRepository.save(
                    new FriendRelation(appUser,
                            friend,
                            now,
                            false,
                            request.selfIntroduction())
            );
        }
    }

    @Transactional
    public void rejectFriendRequest(Long friendUserId) {
        AppUser appUser = appUserService.getLoggedInAppUser();
        AppUser friend = appUserService.getUserById(friendUserId);

        FriendRelation relation = friendRelationRepository
                .findByAppUserAndFriend(friend, appUser)
                .orElseThrow(() ->
                        new FriendRelationNotFoundException(appUser.getId(), friendUserId));

        relation.setRejected(true);
    }

    @Transactional
    public void acceptFriendRequest(Long friendUserId) {
        AppUser appUser = appUserService.getLoggedInAppUser();
        AppUser friend = appUserService.getUserById(friendUserId);

        friendRelationRepository
                .deleteAllByAppUserAndFriendAndAcceptedFalse(appUser, friend);
        friendRelationRepository
                .deleteAllByAppUserAndFriendAndAcceptedFalse(friend, appUser);

        addFriend(appUser.getId(), friendUserId);
    }

    @Transactional
    public void addFriend(Long userId1, Long userId2) {
        AppUser user1 = appUserService.getUserById(userId1);
        AppUser user2 = appUserService.getUserById(userId2);
        LocalDateTime now = LocalDateTime.now();

        friendRelationRepository.saveAll(List.of(
                new FriendRelation(user1, user2, now, true, null),
                new FriendRelation(user2, user1, now, true, null)
        ));
    }

    public LocalDateTime getLatestInteractionTime(Long friendUserId) {
        AppUser appUser = appUserService.getLoggedInAppUser();
        AppUser friend = appUserService.getUserById(friendUserId);
        return friendRelationRepository
                .findByAppUserAndFriend(appUser, friend)
                .orElseThrow(() ->
                        new FriendRelationNotFoundException(appUser.getId(), friendUserId))
                .getLatestInteractionTime();
    }

    @Transactional
    public void updateLatestInteractionTime(AppUser user1,
                                            AppUser user2,
                                            LocalDateTime localDateTime) {
        friendRelationRepository
                .findByAppUserAndFriend(user1, user2)
                .orElseThrow(() ->
                        new FriendRelationNotFoundException(user1.getId(), user2.getId()))
                .setLatestInteractionTime(localDateTime);
        friendRelationRepository
                .findByAppUserAndFriend(user2, user1)
                .orElseThrow(() ->
                        new FriendRelationNotFoundException(user2.getId(), user1.getId()))
                .setLatestInteractionTime(localDateTime);
    }
}

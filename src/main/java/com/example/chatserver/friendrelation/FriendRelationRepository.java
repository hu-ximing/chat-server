package com.example.chatserver.friendrelation;

import com.example.chatserver.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRelationRepository extends JpaRepository<FriendRelation, Long> {

    List<FriendRelation> findByAppUserAndAcceptedTrueOrderByLatestInteractionTimeDesc(AppUser appUser);

    @Query("SELECT fr FROM FriendRelation fr " +
            "WHERE fr.friend = :appUser " +
            "AND fr.accepted = false " +
            "AND fr.rejected = false " +
            "ORDER BY fr.latestInteractionTime DESC")
    List<FriendRelation> findReceivedFriendRequests(AppUser appUser);

    Optional<FriendRelation> findByAppUserAndFriend(AppUser appUser, AppUser friend);

    void deleteAllByAppUserAndFriendAndAcceptedFalse(AppUser appUser, AppUser friend);
}

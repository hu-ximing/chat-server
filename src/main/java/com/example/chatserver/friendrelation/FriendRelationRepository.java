package com.example.chatserver.friendrelation;

import com.example.chatserver.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRelationRepository extends JpaRepository<FriendRelation, Long> {

    List<FriendRelation> findByAppUserOrderByLatestInteractionTimeDesc(AppUser appUser);

    Optional<FriendRelation> findByAppUserAndFriend(AppUser appUser, AppUser friend);
}

package com.example.chatserver.message;

import com.example.chatserver.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findMessagesBySenderAndReceiverOrderByTimestamp(AppUser sender, AppUser receiver);

    @Query("SELECT m FROM Message m " +
            "WHERE (m.sender = :user1 AND m.receiver = :user2) " +
            "OR (m.sender = :user2 AND m.receiver = :user1) " +
            "ORDER BY m.timestamp")
    List<Message> findByAppUsersOrderByTimestamp(AppUser user1, AppUser user2);
}

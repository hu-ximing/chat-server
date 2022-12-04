package com.example.chatserver.message;

import com.example.chatserver.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m " +
            "WHERE (m.sender = :user1 AND m.receiver = :user2) " +
            "OR (m.sender = :user2 AND m.receiver = :user1) " +
            "ORDER BY m.timestamp")
    List<Message> findByAppUsersOrderByTimestamp(AppUser user1, AppUser user2);

    @Query("SELECT count(m) FROM Message m " +
            "WHERE m.sender = :sender " +
            "AND m.receiver = :receiver " +
            "AND m.isRead = false")
    Long countBySenderAndReceiverAndIsReadIsFalse(AppUser sender, AppUser receiver);

    @Transactional
    @Modifying
    @Query("UPDATE Message m " +
            "SET m.isRead = true " +
            "WHERE m.id IN " +
            "(SELECT m.id FROM m " +
            "WHERE m.sender = :sender " +
            "AND m.receiver = :receiver)")
    void setIsReadToTrueBySenderAndReceiver(AppUser sender, AppUser receiver);
}

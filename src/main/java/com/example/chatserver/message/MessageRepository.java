package com.example.chatserver.message;

import com.example.chatserver.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findMessagesBySenderAndReceiverOrderByTimestamp(AppUser sender, AppUser receiver);
}

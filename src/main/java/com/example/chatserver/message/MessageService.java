package com.example.chatserver.message;

import com.example.chatserver.appuser.AppUser;
import com.example.chatserver.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class MessageService {
    private final MessageRepository messageRepository;
    private final AppUserService appUserService;

    public List<Message> getMessages(Long currentUserId, Long friendUserId) {
        AppUser sender = appUserService.getUser(currentUserId);
        AppUser receiver = appUserService.getUser(friendUserId);
        List<Message> messages = new ArrayList<>();
        messages.addAll(messageRepository
                .findMessagesBySenderAndReceiverOrderByTimestamp(sender, receiver));
        messages.addAll(messageRepository
                .findMessagesBySenderAndReceiverOrderByTimestamp(receiver, sender));
        Collections.sort(messages);
        return messages;
    }

    public List<MessageSummary> getMessageSummaries(Long currentUserId, Long friendUserId) {
        return getMessages(currentUserId, friendUserId)
                .stream()
                .map(MessageSummary::new)
                .collect(Collectors.toList());
    }

    public Long countUnreadMessages(Long currentUserId, Long friendUserId) {
        AppUser sender = appUserService.getUser(friendUserId);
        AppUser receiver = appUserService.getUser(currentUserId);
        List<Message> messages = messageRepository
                .findMessagesBySenderAndReceiverOrderByTimestamp(sender, receiver);
        return messages
                .stream()
                .filter(m -> !m.getIsRead())
                .count();
    }

    public void readMessage(Long currentUserId, Long friendUserId) {
        AppUser sender = appUserService.getUser(currentUserId);
        AppUser receiver = appUserService.getUser(friendUserId);
        List<Message> messages = messageRepository
                .findMessagesBySenderAndReceiverOrderByTimestamp(sender, receiver);
        messages.stream()
                .filter(m -> !m.getIsRead())
                .forEach(m -> m.setIsRead(true));
    }

    public void sendMessage(Message message) {
        message.setTimestamp(LocalDateTime.now());
        message.setIsRead(false);
        messageRepository.save(message);
    }

    public LocalDateTime getLastInteractionTime(Long currentUserId, Long friendUserId) {
        List<Message> messages = getMessages(currentUserId, friendUserId);
        return messages.get(messages.size() - 1).getTimestamp();
    }
}

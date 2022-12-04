package com.example.chatserver.message;

import com.example.chatserver.appuser.AppUser;
import com.example.chatserver.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final AppUserService appUserService;

    public List<Message> getMessages(Long currentUserId, Long friendUserId) {
        AppUser sender = appUserService.getUser(currentUserId);
        AppUser receiver = appUserService.getUser(friendUserId);
        return messageRepository
                .findByAppUsersOrderByTimestamp(sender, receiver);
    }

    public List<MessageSummary> getMessageSummaries(Long currentUserId, Long friendUserId) {
        return getMessages(currentUserId, friendUserId)
                .stream()
                .map(m -> new MessageSummary(m.getId(),
                        m.getSender().getId(),
                        m.getReceiver().getId(),
                        m.getTimestamp(),
                        m.getContent(),
                        m.getIsRead()))
                .collect(Collectors.toList());
    }

    public Long countUnreadMessages(Long currentUserId, Long friendUserId) {
        AppUser current = appUserService.getUser(currentUserId);
        AppUser friend = appUserService.getUser(friendUserId);
        return messageRepository
                .countBySenderAndReceiverAndIsReadIsFalse(friend, current);
    }

    @Transactional
    public void readMessage(Long currentUserId, Long friendUserId) {
        AppUser current = appUserService.getUser(currentUserId);
        AppUser friend = appUserService.getUser(friendUserId);
        messageRepository
                .setIsReadToTrueBySenderAndReceiver(friend, current);
    }

    @Transactional
    public void sendMessage(MessageSendRequest request) {
        AppUser sender = appUserService.getUser(request.senderId());
        AppUser receiver = appUserService.getUser(request.receiverId());
        Message message = new Message(
                sender,
                receiver,
                LocalDateTime.now(),
                request.content(),
                false
        );
        messageRepository.save(message);
    }

    public LocalDateTime getLastInteractionTime(Long currentUserId, Long friendUserId) {
        List<Message> messages = getMessages(currentUserId, friendUserId);
        return messages.get(messages.size() - 1).getTimestamp();
    }
}

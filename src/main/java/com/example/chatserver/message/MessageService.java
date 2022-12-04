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

    public List<Message> getMessagesWith(Long friendUserId) {
        AppUser user1 = appUserService.getLoggedInAppUser();
        AppUser user2 = appUserService.getUserById(friendUserId);
        return messageRepository
                .findByAppUsersOrderByTimestamp(user1, user2);
    }

    public List<MessageSummary> getMessageSummariesWith(Long friendUserId) {
        return getMessagesWith(friendUserId)
                .stream()
                .map(m -> new MessageSummary(m.getId(),
                        m.getSender().getId(),
                        m.getReceiver().getId(),
                        m.getTimestamp(),
                        m.getContent(),
                        m.getIsRead()))
                .collect(Collectors.toList());
    }

    public Long countUnreadMessages(Long friendUserId) {
        AppUser current = appUserService.getLoggedInAppUser();
        AppUser friend = appUserService.getUserById(friendUserId);
        return messageRepository
                .countBySenderAndReceiverAndIsReadIsFalse(friend, current);
    }

    @Transactional
    public void readMessage(Long friendUserId) {
        AppUser current = appUserService.getLoggedInAppUser();
        AppUser friend = appUserService.getUserById(friendUserId);
        messageRepository
                .setIsReadToTrueBySenderAndReceiver(friend, current);
    }

    @Transactional
    public void sendMessageTo(MessageSendRequest request) {
        AppUser sender = appUserService.getLoggedInAppUser();
        AppUser receiver = appUserService.getUserById(request.receiverId());
        Message message = new Message(
                sender,
                receiver,
                LocalDateTime.now(),
                request.content(),
                false
        );
        messageRepository.save(message);
    }

    @Transactional
    public void createMessage(AppUser sender, MessageSendRequest request) {
        AppUser receiver = appUserService.getUserById(request.receiverId());
        Message message = new Message(
                sender,
                receiver,
                LocalDateTime.now(),
                request.content(),
                false
        );
        messageRepository.save(message);
    }

    public LocalDateTime getLastInteractionTime(Long friendUserId) {
        List<Message> messages = getMessagesWith(friendUserId);
        return messages.get(messages.size() - 1).getTimestamp();
    }
}

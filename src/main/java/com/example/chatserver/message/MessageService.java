package com.example.chatserver.message;

import com.example.chatserver.appuser.AppUser;
import com.example.chatserver.appuser.AppUserService;
import com.example.chatserver.friendrelation.FriendRelationService;
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
    private final FriendRelationService friendRelationService;

    public boolean checkIsFriendWith(Long AppUserId) {
        // TODO: Check friend is really a friend, if not throw new FriendRelationNotFoundException
        return true;
    }

    public List<Message> getMessagesWith(Long friendUserId) {
        AppUser user1 = appUserService.getLoggedInAppUser();
        AppUser user2 = appUserService.getUserById(friendUserId);
        return messageRepository
                .findByAppUsersOrderByTimestamp(user1, user2);
    }

    public List<MessageSummary> getMessageSummariesWith(Long friendUserId) {
        checkIsFriendWith(friendUserId);
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
        checkIsFriendWith(friendUserId);
        AppUser current = appUserService.getLoggedInAppUser();
        AppUser friend = appUserService.getUserById(friendUserId);
        return messageRepository
                .countBySenderAndReceiverAndIsReadIsFalse(friend, current);
    }

    @Transactional
    public void readMessage(Long friendUserId) {
        checkIsFriendWith(friendUserId);
        AppUser current = appUserService.getLoggedInAppUser();
        AppUser friend = appUserService.getUserById(friendUserId);
        messageRepository
                .setIsReadToTrueBySenderAndReceiver(friend, current);
    }

    @Transactional
    public void sendMessageTo(MessageSendRequest request) {
        checkIsFriendWith(request.receiverId());
        AppUser sender = appUserService.getLoggedInAppUser();
        AppUser receiver = appUserService.getUserById(request.receiverId());
        LocalDateTime now = LocalDateTime.now();
        Message message = new Message(
                sender,
                receiver,
                now,
                request.content(),
                false
        );
        messageRepository.save(message);
        friendRelationService.updateLatestInteractionTime(sender, receiver, now);
    }

    @Transactional
    public void createMessage(Long senderId, MessageSendRequest request) {
        checkIsFriendWith(request.receiverId());
        AppUser sender = appUserService.getUserById(senderId);
        AppUser receiver = appUserService.getUserById(request.receiverId());
        LocalDateTime now = LocalDateTime.now();
        Message message = new Message(
                sender,
                receiver,
                now,
                request.content(),
                false
        );
        messageRepository.save(message);
        friendRelationService.updateLatestInteractionTime(sender, receiver, now);
    }
}

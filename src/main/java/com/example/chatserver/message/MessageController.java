package com.example.chatserver.message;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/message")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    /**
     * Get message summaries by friendUserId
     * sorted by timestamp, with the latest message at the end of the list
     *
     * @param friendUserId id of friend user
     * @return a list of message summaries
     */
    @GetMapping
    public List<MessageSummary> getSortedMessageSummaries(@RequestParam Long friendUserId) {
        return messageService.getMessageSummariesWith(friendUserId);
    }

    /**
     * Count number of messages that have not been read
     *
     * @param friendUserId id of friend user
     * @return number of messages that have not been read
     */
    @GetMapping(path = "count")
    public Long countUnreadMessages(@RequestParam Long friendUserId) {
        return messageService.countUnreadMessages(friendUserId);
    }

    /**
     * Send a message.
     *
     * @param request MessageSendRequest object
     */
    @PutMapping
    public void sendMessageTo(@RequestBody MessageSendRequest request) {
        messageService.sendMessageTo(request);
    }

    /**
     * Read all messages sent from the friend.
     *
     * @param friendUserId id of friend user
     */
    @PostMapping(path = "read")
    public void readMessage(@RequestParam Long friendUserId) {
        messageService.readMessage(friendUserId);
    }

    /**
     * Get the latest interaction time with the friend.
     *
     * @param friendUserId id of friend user
     * @return The most recent message's timestamp
     */
    @GetMapping(path = "last-interaction-time")
    public LocalDateTime getLastInteractionTime(@RequestParam Long friendUserId) {
        return messageService.getLastInteractionTime(friendUserId);
    }
}

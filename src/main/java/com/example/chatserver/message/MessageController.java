package com.example.chatserver.message;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/message")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    /**
     * Get a list of message summary by friendUserId.
     * The list is sorted by the message's timestamp from earliest to latest.
     *
     * @param friendUserId id of friend user
     * @return a list of message summaries
     */
    @GetMapping
    public List<MessageSummary> getSortedMessageSummaries(@RequestParam Long friendUserId) {
        return messageService.getMessageSummariesWith(friendUserId);
    }

    /**
     * Count number of messages that have not been read.
     *
     * @param friendUserId id of friend user
     * @return number of messages that have not been read
     */
    @GetMapping(path = "count-unread")
    public Long countUnreadMessages(@RequestParam Long friendUserId) {
        return messageService.countUnreadMessages(friendUserId);
    }

    /**
     * Send a message.
     *
     * @param request MessageSendRequest object
     */
    @PostMapping
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
}

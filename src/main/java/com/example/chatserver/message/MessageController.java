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

    @GetMapping
    public List<MessageSummary> getMessageSummaries(@RequestParam Long currentUserId,
                                                    @RequestParam Long friendUserId) {
        return messageService.getMessageSummaries(currentUserId, friendUserId);
    }

    @GetMapping(path = "count")
    public Long countUnreadMessages(@RequestParam Long currentUserId,
                                    @RequestParam Long friendUserId) {
        return messageService.countUnreadMessages(currentUserId, friendUserId);
    }

    @PostMapping(path = "{userId}")
    public void sendMessage(@PathVariable("userId") Long senderId,
                            @RequestBody MessageSendRequest request) {
        messageService.sendMessage(request);
    }

    @PostMapping(path = "read")
    public void readMessage(@RequestParam Long currentUserId,
                            @RequestParam Long friendUserId) {
        messageService.readMessage(currentUserId, friendUserId);
    }

    @GetMapping(path = "last-interaction-time")
    public LocalDateTime getLastInteractionTime(@RequestParam Long currentUserId,
                                                @RequestParam Long friendUserId) {
        return messageService.getLastInteractionTime(currentUserId, friendUserId);
    }
}

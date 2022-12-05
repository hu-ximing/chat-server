package com.example.chatserver.friendrelation;

import com.example.chatserver.appuser.AppUserSummary;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/friend")
@AllArgsConstructor
public class FriendRelationController {

    private final FriendRelationService friendRelationService;

    /**
     * Get a list of users' summaries that are friends of the current logged-in user
     * The list is sorted by their last interaction time,
     * the friend interacted most recently comes first.
     *
     * @return a list of users summaries
     */
    @GetMapping
    public List<AppUserSummary> getFriends() {
        return friendRelationService.getFriends();
    }

    /**
     * Add friend with the user with id friendUserId.
     *
     * @param friendUserId id of the user
     *                     who the current logged-in user want to be friend with
     */
    @PostMapping
    public void addFriendWith(@RequestParam Long friendUserId) {
        friendRelationService.addFriendWith(friendUserId);
    }

    /**
     * Get the latest interaction time with the friend.
     *
     * @param friendUserId id of friend user
     * @return The most recent message's timestamp
     */
    @GetMapping(path = "last-interaction-time")
    public LocalDateTime getLatestInteractionTime(@RequestParam Long friendUserId) {
        return friendRelationService.getLatestInteractionTime(friendUserId);
    }
}

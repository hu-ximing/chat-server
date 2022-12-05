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
     * @return a list of user summaries
     */
    @GetMapping
    public List<AppUserSummary> getFriends() {
        return friendRelationService.getFriends();
    }

    /**
     * Get a list of received friend requests from other users
     * that haven't been accepted or rejected by the current logged-in user.
     * The list is sorted by the request sent time,
     * the request sent most recently comes first.
     *
     * @return a list of user summaries
     */
    @GetMapping(path = "request")
    public List<AppUserSummary> getReceivedFriendRequests() {
        return friendRelationService.getReceivedFriendRequests();
    }

    /**
     * Send a request to a user
     *
     * @param request information about the request
     */
    @PostMapping(path = "request")
    public void sendFriendRequest(FriendRelationRequest request) {
        friendRelationService.sendFriendRequest(request);
    }

    /**
     * Reject a user's friend request.
     * Rejected requests won't be included when getting the list of received requests.
     *
     * @param friendUserId user id of request
     */
    @PostMapping(path = "request/reject")
    public void rejectFriendRequest(Long friendUserId) {
        friendRelationService.rejectFriendRequest(friendUserId);
    }

    /**
     * Accept a user's friend request.
     * Accepted requests won't be included with getting the list of received requests.
     *
     * @param friendUserId user id of request
     */
    @PostMapping(path = "request/accept")
    public void acceptFriendRequest(Long friendUserId) {
        friendRelationService.acceptFriendRequest(friendUserId);
    }

    /**
     * Add friend with the user with id friendUserId.
     *
     * @param friendUserId id of the user
     *                     who the current logged-in user want to be friend with
     */
    @Deprecated
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

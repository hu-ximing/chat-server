package com.example.chatserver.appuser;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/user")
@AllArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    /**
     * Get the current logged-in user's details
     *
     * @return user object
     */
    @GetMapping
    public AppUser getAppUser() {
        return appUserService.getLoggedInAppUser();
    }

    /**
     * Delete the current logged-in user
     */
    @DeleteMapping
    public void deleteAppUser() {
        appUserService.deleteAppUser();
    }

    /**
     * Update the current logged-in user's information
     *
     * @param username    new username
     * @param password    new password
     * @param displayName new display name
     * @param firstName   new first name
     * @param lastName    new last name
     */
    @PutMapping
    public void updateAppUser(@RequestParam(required = false) String username,
                              @RequestParam(required = false) String password,
                              @RequestParam(required = false) String displayName,
                              @RequestParam(required = false) String firstName,
                              @RequestParam(required = false) String lastName) {
        appUserService.updateAppUser(username, password, displayName, firstName, lastName);
    }

    /**
     * Get a list of users' summaries that are friends of the current logged-in user
     * The list is sorted by their last interaction time,
     * the friend interacted most recently comes first.
     *
     * @return a list of users summaries
     */
    @GetMapping(path = "friends")
    public List<AppUserSummary> getFriends() {
        return appUserService.getFriends();
    }

    /**
     * Make friend with the user with id friendUserId.
     *
     * @param friendUserId id of the user
     *                     who the current logged-in user want to be friend with
     */
    @PostMapping(path = "add-friend")
    public void addFriend(@RequestParam Long friendUserId) {
        appUserService.addFriendWith(friendUserId);
    }
}

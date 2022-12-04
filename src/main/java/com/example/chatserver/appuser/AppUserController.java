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
     * Get a user's details by user id
     *
     * @param userId id of user to be searched
     * @return user object
     */
    @GetMapping
    public AppUser getUser(@RequestParam Long userId) {
        return appUserService.getUser(userId);
    }

    /**
     * Register a new user
     *
     * @param appUser user object containing registration information of the new user
     */
    @PostMapping
    public void registerNewUser(@RequestBody AppUser appUser) {
        appUserService.registerNewUser(appUser);
    }

    /**
     * Delete a user by user id
     *
     * @param userId id of the user to be deleted
     */
    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        appUserService.deleteUser(userId);
    }

    /**
     * Update a user's information by user id
     *
     * @param userId      id of the user to be updated
     * @param displayName updated display name
     * @param username    updated username
     * @param password    updated password
     */
    @PutMapping(path = "{userId}")
    public void updateUser(@PathVariable("userId") Long userId,
                           @RequestParam(required = false) String displayName,
                           @RequestParam(required = false) String username,
                           @RequestParam(required = false) String password) {
        appUserService.updateUser(userId, displayName, username, password);
    }

    /**
     * Get a list of users that are friends to a user by user id.
     * The list is sorted by their last interaction time.
     *
     * @param userId id of the user to be searched
     * @return a list of users
     */
    @GetMapping(path = "friends")
    public List<AppUser> getFriends(@RequestParam Long userId) {
        return appUserService.getFriends(userId);
    }

    @PostMapping(path = "add-friend")
    public void addFriend(@RequestParam Long userId,
                          @RequestParam Long friendUserId) {
        appUserService.addFriend(userId, friendUserId);
    }

    @PostMapping(path = "login")
    public AppUser userLogin(@RequestParam String username,
                             @RequestParam String password) {
        return appUserService.userLogin(username, password);
    }
}

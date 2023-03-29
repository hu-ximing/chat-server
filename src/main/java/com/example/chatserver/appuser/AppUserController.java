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
     * Search a single user by id.
     * If user with id exists, return summary of that user;
     * if user with id does not exist, return null.
     *
     * @param appUserId id of the user
     * @return appUserSummary or null
     */
    @GetMapping(path = "search/id")
    public AppUserDTO searchUserById(Long appUserId) {
        return appUserService.searchUserById(appUserId);
    }

    /**
     * Search a single user by username.
     * If user with username exists, return summary of that user;
     * if user with username does not exist, return null.
     *
     * @param username username of the user
     * @return appUserSummary or null
     */
    @GetMapping(path = "search/username")
    public AppUserDTO searchUserByUsername(String username) {
        return appUserService.searchUserByUsername(username);
    }

    /**
     * Search a list of user by display name (regular expression).
     *
     * @param displayNameRegex display name (regular expression) of the users.
     * @return a list of appUserSummary
     */
    @GetMapping(path = "search/display-name")
    public List<AppUserDTO> searchUserByDisplayNameRegex(String displayNameRegex) {
        return appUserService.searchUserByDisplayNameRegex(displayNameRegex);
    }
}

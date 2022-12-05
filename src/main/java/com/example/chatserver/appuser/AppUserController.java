package com.example.chatserver.appuser;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}

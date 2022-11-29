package com.example.chatserver.appuser;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/api/user")
@AllArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping
    public AppUser getUser(@RequestParam Long userId) {
        return appUserService.getUser(userId);
    }

    @PostMapping
    public void registerNewUser(@RequestBody AppUser appUser) {
        appUserService.registerNewUser(appUser);
    }

    @DeleteMapping
    public void deleteUser(@RequestParam Long userId) {
        appUserService.deleteUser(userId);
    }

    @PutMapping
    public void updateUser(@RequestParam Long userId,
                           @RequestParam(required = false) String displayName,
                           @RequestParam(required = false) String username,
                           @RequestParam(required = false) String password,
                           @RequestParam(required = false) LocalDate birthDate) {
        appUserService.updateUser(userId, displayName, username, password, birthDate);
    }

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

package com.example.chatserver.appuser;

import com.example.chatserver.appuser.exception.AppUserNotFoundException;
import com.example.chatserver.appuser.exception.UsernameTakenException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "user with username " + username + " not found"));
    }

    public AppUser getLoggedInAppUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (AppUser) auth.getPrincipal();
    }

    public AppUser getUserById(Long userId) {
        return appUserRepository.findById(userId).orElseThrow(
                () -> new AppUserNotFoundException(userId)
        );
    }

    public String registerNewUser(AppUser appUser) {
        Optional<AppUser> userOptional = appUserRepository.findByUsername(appUser.getUsername());
        if (userOptional.isPresent()) {
            throw new UsernameTakenException(appUser.getUsername());
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);
        return "registration succeed";
    }

    public void deleteAppUser() {
        appUserRepository.delete(getLoggedInAppUser());
    }

    @Transactional
    public void updateAppUser(
            String username,
            String password,
            String displayName,
            String firstName,
            String lastName) {
        AppUser appUser = getLoggedInAppUser();
        if (username != null &&
                username.length() > 0 &&
                !Objects.equals(username, appUser.getUsername())) {
            if (appUserRepository.findByUsername(username).isPresent()) {
                throw new UsernameTakenException(username);
            }
            appUser.setUsername(username);
        }
        if (password != null &&
                password.length() > 0) {
            String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
            appUser.setPassword(encodedPassword);
        }
        if (displayName != null &&
                displayName.length() > 0 &&
                !Objects.equals(displayName, appUser.getDisplayName())) {
            appUser.setDisplayName(displayName);
        }
        if (firstName != null &&
                firstName.length() > 0 &&
                !Objects.equals(firstName, appUser.getFirstName())) {
            appUser.setFirstName(firstName);
        }
        if (lastName != null &&
                lastName.length() > 0 &&
                !Objects.equals(lastName, appUser.getLastName())) {
            appUser.setLastName(lastName);
        }
    }

    public List<AppUserSummary> getFriends() {
        // TODO: Sort friends by interaction time

        // since @ManyToMany(fetch = FetchType.LAZY) List<AppUser> friends
        // you need to load the user from database
        AppUser appUser = getUserById(getLoggedInAppUser().getId());
        return appUser
                .getFriends()
                .stream()
                .map(f -> new AppUserSummary(
                        f.getId(),
                        f.getFirstName(),
                        f.getLastName(),
                        f.getDisplayName()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addFriendWith(Long friendUserId) {
        AppUser appUser = getLoggedInAppUser();
        AppUser friend = getUserById(friendUserId);
        appUser.getFriends().add(friend);
        friend.getFriends().add(appUser);
    }

    @Transactional
    public void addFriend(Long userId1, Long userId2) {
        AppUser user1 = getUserById(userId1);
        AppUser user2 = getUserById(userId2);
        user1.getFriends().add(user2);
        user2.getFriends().add(user1);
    }
}

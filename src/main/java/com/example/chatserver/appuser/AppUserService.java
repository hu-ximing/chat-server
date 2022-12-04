package com.example.chatserver.appuser;

import com.example.chatserver.appuser.exception.AppUserNotFoundException;
import com.example.chatserver.appuser.exception.UsernameTakenException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "user with username " + username + " not found"));
    }

    public AppUser getUser(Long userId) {
        return appUserRepository.findById(userId).orElseThrow(
                () -> new AppUserNotFoundException(userId)
        );
    }

    public void registerNewUser(AppUser appUser) {
        Optional<AppUser> userOptional = appUserRepository.findByUsername(appUser.getUsername());
        if (userOptional.isPresent()) {
            throw new UsernameTakenException(appUser.getUsername());
        }
        appUserRepository.save(appUser);
    }

    public void deleteUser(Long userId) {
        if (!appUserRepository.existsById(userId)) {
            throw new AppUserNotFoundException(userId);
        }
        appUserRepository.deleteById(userId);
    }

    public void updateUser(Long userId,
                           String displayName,
                           String username,
                           String password) {
        AppUser appUser = getUser(userId);
        if (displayName != null &&
                displayName.length() > 0 &&
                !Objects.equals(displayName, appUser.getDisplayName())) {
            appUser.setDisplayName(displayName);
        }
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
            appUser.setPassword(password);
        }
    }

    public List<AppUser> getFriends(Long userId) {
        AppUser appUser = getUser(userId);
        return appUser.getFriends();
    }

    public void addFriend(Long userId, Long friendUserId) {
        AppUser appUser = getUser(userId);
        AppUser friend = getUser(friendUserId);
        appUser.getFriends().add(friend);
        friend.getFriends().add(appUser);
    }

    public AppUser userLogin(String username, String password) {
        AppUser appUser = appUserRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("Could not find username " + username)
        );
        if (appUser.getPassword().equals(password)) {
            return appUser;
        } else {
            return null;
        }
    }
}

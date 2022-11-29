package com.example.chatserver.appuser;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class AppUserService {
    private final AppUserRepository appUserRepository;

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
                           String password,
                           LocalDate birthDate) {
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
        if (birthDate != null) {
            appUser.setBirthDate(birthDate);
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

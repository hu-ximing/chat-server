package com.example.chatserver.registration;

import com.example.chatserver.appuser.AppUser;
import com.example.chatserver.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.username());
        if (!isValidEmail) {
            throw new RuntimeException("email is not valid: " + request.username());
        }
        return appUserService.registerNewUser(
                new AppUser(
                        request.username(),
                        request.password(),
                        request.firstName(),
                        request.lastName(),
                        request.displayName()
                )
        );
    }
}

package com.example.chatserver;

import com.example.chatserver.appuser.AppUser;
import com.example.chatserver.appuser.AppUserService;
import com.example.chatserver.message.MessageSendRequest;
import com.example.chatserver.message.MessageService;
import com.example.chatserver.registration.RegistrationRequest;
import com.example.chatserver.registration.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@AllArgsConstructor
public class ChatServerApplication {

    private final AppUserService appUserService;
    private final MessageService messageService;
    private final RegistrationService registrationService;

    public static void main(String[] args) {
        SpringApplication.run(ChatServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            registrationService.register(new RegistrationRequest(
                    "babara@example.com",
                    "123",
                    "Babara",
                    "MacCaffrey",
                    "Babara"
            ));
            registrationService.register(new RegistrationRequest(
                    "elka@example.com",
                    "123",
                    "Elka",
                    "Twiddell",
                    "elkaaa"
            ));
            registrationService.register(new RegistrationRequest(
                    "tom@example.com",
                    "123",
                    "Tom",
                    "C",
                    "tom"
            ));
            AppUser babara = appUserService.getUser(1L);
            AppUser elka = appUserService.getUser(2L);
            AppUser tom = appUserService.getUser(3L);
            appUserService.addFriend(babara.getId(), tom.getId());
            appUserService.addFriend(elka.getId(), tom.getId());
            messageService.sendMessage(new MessageSendRequest(
                    tom.getId(), babara.getId(), "first message to babara"
            ));
            messageService.sendMessage(new MessageSendRequest(
                    babara.getId(), tom.getId(), "second message to tom"
            ));
        };
    }
}

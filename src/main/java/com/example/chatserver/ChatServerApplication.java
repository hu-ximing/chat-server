package com.example.chatserver;

import com.example.chatserver.appuser.AppUser;
import com.example.chatserver.appuser.AppUserService;
import com.example.chatserver.friendrelation.FriendRelationService;
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
    private final FriendRelationService friendRelationService;

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
            AppUser babara = appUserService.getUserById(1L);
            AppUser elka = appUserService.getUserById(2L);
            AppUser tom = appUserService.getUserById(3L);

            friendRelationService.addFriend(tom.getId(), babara.getId());
            friendRelationService.addFriend(tom.getId(), elka.getId());

            messageService.createMessage(elka.getId(), new MessageSendRequest(
                    tom.getId(), "elka to tom 2-3"
            ));
            Thread.sleep(1000);
            messageService.createMessage(tom.getId(), new MessageSendRequest(
                    babara.getId(), "tom to babara 3-1"
            ));
            Thread.sleep(1000);
            messageService.createMessage(babara.getId(), new MessageSendRequest(
                    tom.getId(), "babara to tom 1-3"
            ));

        };
    }
}

package com.example.chatserver;

import com.example.chatserver.appuser.AppUser;
import com.example.chatserver.appuser.AppUserRepository;
import com.example.chatserver.appuser.AppUserService;
import com.example.chatserver.message.MessageSendRequest;
import com.example.chatserver.message.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class ChatServerApplication {
    private final AppUserService appUserService;
    private final MessageService messageService;

    public static void main(String[] args) {
        SpringApplication.run(ChatServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AppUserRepository appUserRepository) {
        return args -> {
            AppUser babara = new AppUser(
                    "babara@example.com",
                    "123",
                    "Babara",
                    "MacCaffrey",
                    "Babara"
            );
            AppUser elka = new AppUser(
                    "elka@example.com",
                    "123",
                    "Elka",
                    "Twiddell",
                    "elkaaa"
            );
            AppUser tom = new AppUser(
                    "tom@example.com",
                    "123",
                    "Tom",
                    "C",
                    "tom"
            );

            appUserRepository.saveAll(List.of(
                    babara, elka, tom
            ));
            appUserService.addFriend(tom.getId(), babara.getId());
            appUserService.addFriend(tom.getId(), elka.getId());
            messageService.sendMessage(new MessageSendRequest(
                    tom.getId(), babara.getId(), "first message to babara"
            ));
            messageService.sendMessage(new MessageSendRequest(
                    tom.getId(), babara.getId(), "second message to tom"
            ));
        };
    }
}

package com.example.chatserver;

import com.example.chatserver.appuser.AppUser;
import com.example.chatserver.appuser.AppUserRepository;
import com.example.chatserver.appuser.AppUserService;
import com.example.chatserver.message.Message;
import com.example.chatserver.message.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
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
                    "Babara",
                    "babara@example.com",
                    "123",
                    LocalDate.of(1986, 3, 28));
            AppUser ines = new AppUser(
                    "Ines",
                    "ines@example.com",
                    "123",
                    LocalDate.of(1986, 4, 13));
            AppUser freddi = new AppUser(
                    "Freddi",
                    "freddi@example.com",
                    "123",
                    LocalDate.of(1985, 2, 7));
            AppUser ambur = new AppUser(
                    "Ambur",
                    "ambur@example.com",
                    "123",
                    LocalDate.of(1974, 4, 14));
            AppUser clemmie = new AppUser(
                    "Clemmie",
                    "clemmie@example.com",
                    "123",
                    LocalDate.of(1973, 11, 7));
            AppUser tom = new AppUser(
                    "Tom",
                    "tom@example.com",
                    "123",
                    LocalDate.of(1991, 9, 4));

            appUserRepository.saveAll(List.of(
                    babara, ines, freddi, ambur, clemmie, tom
            ));
            appUserService.addFriend(tom.getId(), babara.getId());
            appUserService.addFriend(tom.getId(), ines.getId());
            appUserService.addFriend(tom.getId(), freddi.getId());
            appUserService.addFriend(tom.getId(), ambur.getId());
            appUserService.addFriend(tom.getId(), clemmie.getId());
            messageService.sendMessage(new Message(
                    tom, babara, null, "hello, babara"
            ));
            messageService.sendMessage(new Message(
                    babara, tom, null, "hello, tom"
            ));
        };
    }
}

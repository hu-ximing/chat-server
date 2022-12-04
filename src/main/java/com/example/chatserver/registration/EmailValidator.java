package com.example.chatserver.registration;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(String email) {
        return Pattern.compile("^(.+)@(\\S+)$")
                .matcher(email)
                .find();
    }
}

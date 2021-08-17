package com.liftoff.libraryapp.registration;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(String s) {
        // TODO: Enter REGEX to validate email
        return true;
    }
}

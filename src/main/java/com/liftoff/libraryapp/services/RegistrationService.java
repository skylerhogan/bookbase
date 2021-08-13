package com.liftoff.libraryapp.services;


// **** Description *** //
// This class takes the RegistrationRequest object //
// **** ********** *** //

import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    public String register(RegistrationRequest request) {
        return "it works";
    }
}

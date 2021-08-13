package com.liftoff.libraryapp.controllers;

import com.liftoff.libraryapp.services.RegistrationRequest;
import com.liftoff.libraryapp.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// **** Description *** //
// This class is a controller that handles User Registration requests //
// It receives a RegistrationRequest object and triggers the RegistrationService //
// **** ********** *** //

@RestController
@RequestMapping("api/v1/registration")
public class RegistrationController {

    // Field
    private RegistrationService registrationService;

    // Constructor
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @PostMapping
    public String processRegistration(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }
}

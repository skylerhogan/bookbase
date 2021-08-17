package com.liftoff.libraryapp.registration;

import org.springframework.web.bind.annotation.*;

// **** Description *** //
// This class is a controller that handles User Registration requests //
// It receives a RegistrationRequest object and triggers the RegistrationService //
// **** ********** *** //

@RestController
@RequestMapping("registration")
public class RegistrationController {

    // Field
    private final RegistrationService registrationService;

    // Constructor
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @PostMapping
    public String processRegistration(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirmRegistration(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}

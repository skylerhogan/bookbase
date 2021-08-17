package com.liftoff.libraryapp.registration.token;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

// **** Description *** //
// Service to save confirmation tokens to our DB //
// **** ********** *** //
@Service
public class ConfirmationTokenService {

    // Field
    private final ConfirmationTokenRepository confirmationTokenRepository;

    // Constructor
    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    // Method or Service that is being performed by this class
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    };

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}

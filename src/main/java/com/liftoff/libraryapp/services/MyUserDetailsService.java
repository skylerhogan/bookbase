package com.liftoff.libraryapp.services;


import com.liftoff.libraryapp.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// **** Description *** //
// Service for our UserRepository to use in accessing users based on email //
// **** ********** *** //

@Service
public class MyUserDetailsService implements UserDetailsService {

    // Field
    private final UserRepository userRepository;

    // Constructor
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Override Method of UserDetailsService Class
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email not found"));
    }
}

package com.liftoff.libraryapp.profile;

import com.liftoff.libraryapp.repositories.BookRepository;
import com.liftoff.libraryapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("user")
public class ProfileController {

        @Autowired
        private BookRepository bookRepository;

        @Autowired
        private UserRepository userRepository;

        /*@GetMapping("profile")
        public String displayUserProfile() {
            return "user/profile";
        }*/
    }

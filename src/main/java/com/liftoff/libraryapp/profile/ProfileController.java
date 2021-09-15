package com.liftoff.libraryapp.profile;

import com.liftoff.libraryapp.models.MyUserDetailsService;
import com.liftoff.libraryapp.models.User;
import com.liftoff.libraryapp.repositories.BookRepository;
import com.liftoff.libraryapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("user")
public class ProfileController {

        @Autowired
        private BookRepository bookRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private MyUserDetailsService myUserDetailsService;

        @GetMapping("profile")
        public String displayUserProfile(Model model) {

            /* library stats */
            Integer pagesRead = bookRepository.selectPagesRead();
            Integer pagesToRead = bookRepository.selectPagesToRead();;
            Integer totalBooks = bookRepository.selectTotalBooksInLibrary();
            Integer totalBooksRead = bookRepository.selectTotalBooksRead();
            String favoriteGenre = bookRepository.selectFavoriteGenre();
            String joinDate = bookRepository.selectDateOfFirstBookAdded();
            model.addAttribute("pagesRead", pagesRead);
            model.addAttribute("pagesToRead", pagesToRead);
            model.addAttribute("totalBooks", totalBooks);
            model.addAttribute("totalBooksRead", totalBooksRead);
            model.addAttribute("favoriteGenre", favoriteGenre);
            model.addAttribute("joinDate", joinDate);

            /* get user details */
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = ((UserDetails)principal).getUsername();
            User user = (User) myUserDetailsService.loadUserByUsername(username);
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            model.addAttribute("user", user);
            model.addAttribute("firstName", firstName);
            model.addAttribute("lastName", lastName);
            model.addAttribute("username", username);

            return "user/profile";
        }
    }

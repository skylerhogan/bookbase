package com.liftoff.libraryapp.landing;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String displayHomePage(Model model, Principal user) {
        if (user != null) {
            return "home";
        }
        return "index";
    }
}

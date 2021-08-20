package com.liftoff.libraryapp.landing;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping
    public String hello() {
//        return "hello";
        return "index";
    }
}

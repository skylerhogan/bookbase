package com.liftoff.librarymanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("add")
    public String displayAddBookForm() {
        return "add";
    }
}

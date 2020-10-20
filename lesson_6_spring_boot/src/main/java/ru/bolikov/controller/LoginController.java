package ru.bolikov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginModel() {
        return "login";
    }

//    @PostMapping("/login")
//    public String auth() {
//        return "redirect:/index";
//    }
}

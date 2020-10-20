package ru.bolikov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminPageController {

    @GetMapping("/admin")
    public String fullCapabilities(){
        return "admin";
    }
}

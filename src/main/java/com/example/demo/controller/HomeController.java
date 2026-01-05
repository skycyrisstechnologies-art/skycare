package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // ✅ ROOT URL
    @GetMapping("/")
    public String root() {
        return "home";
    }

    // ✅ OPTIONAL ALIAS
    @GetMapping("/home")
    public String home() {
        return "home";
    }
}

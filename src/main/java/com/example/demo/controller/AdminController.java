package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "admin-dashboard";
    }

    @GetMapping("/billing")
    public String billingPage() {
        return "billing";
    }

    @GetMapping("/analytics")
    public String analyticsPage() {
        return "analytics";
    }
}

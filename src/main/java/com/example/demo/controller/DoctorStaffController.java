package com.example.demo.controller;

import com.example.demo.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorStaffController {

    private final StaffService staffService;

    @GetMapping("/staff")
    public String viewStaffForDoctor(Model model) {

        model.addAttribute("staff", staffService.getAllStaff());

        return "doctor-staff"; // 🔥 new template
    }
}

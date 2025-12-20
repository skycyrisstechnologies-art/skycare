package com.example.demo.controller;

import com.example.demo.entity.Doctor;
import com.example.demo.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/doctors")
public class AdminDoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public String listDoctors(Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "admin-doctors";
    }

    @GetMapping("/add")
    public String addDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "admin-add-doctor";
    }

    @PostMapping("/add")
    public String saveDoctor(@ModelAttribute Doctor doctor) {
        doctorService.saveDoctor(doctor);
        return "redirect:/admin/doctors";
    }

    @GetMapping("/edit/{id}")
    public String editDoctorForm(@PathVariable Long id, Model model) {
        model.addAttribute("doctor", doctorService.getDoctor(id));
        return "admin-edit-doctor";
    }

    @PostMapping("/update/{id}")
    public String updateDoctor(@PathVariable Long id, @ModelAttribute Doctor doctor) {
        doctor.setId(id);
        doctorService.saveDoctor(doctor);
        return "redirect:/admin/doctors";
    }

    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return "redirect:/admin/doctors";
    }
       @GetMapping("/add-doctor")
    public String addDoctor() {
        return "admin-add-doctor";
    }
}


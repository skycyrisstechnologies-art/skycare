package com.example.demo.controller;

import com.example.demo.entity.Patient;
import com.example.demo.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PatientRegistrationController {

    private final PatientRepository patientRepository;

    // 🔹 OPEN patient registration page
    @GetMapping("/patient/register")
    public String showPatientRegistrationForm() {
        return "patient-register"; // HTML file name
    }

    // 🔹 SAVE patient details
    @PostMapping("/patient/register")
    public String registerPatient(Patient patient) {
        patientRepository.save(patient);
        return "redirect:/login"; // or /home if you want
    }
}

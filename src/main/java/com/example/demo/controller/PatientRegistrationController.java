package com.example.demo.controller;

import com.example.demo.entity.Patient;
import com.example.demo.entity.Doctor;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.service.PatientService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
@RequiredArgsConstructor
public class PatientRegistrationController {

    private final PatientService patientService;
    private final DoctorRepository doctorRepository;   // ✅ CHANGED

    // =========================
    // STEP 1 – MOBILE NUMBER
    // =========================
    @GetMapping("/patient/register")
    public String showMobilePage() {
        return "patient-step1";
    }

    @PostMapping("/patient/send-otp")
    public String sendOtp(@RequestParam String phone, HttpSession session) {

        int otp = new Random().nextInt(900000) + 100000;

        session.setAttribute("otp", otp);
        session.setAttribute("phone", phone);

        // TEMP: replace with SMS API later
        System.out.println("OTP is: " + otp);

        return "patient-otp";
    }

    @PostMapping("/patient/verify-otp")
    public String verifyOtp(@RequestParam String otp, HttpSession session) {

        System.out.println("OTP entered: " + otp);

        // TEMP BYPASS
        return "redirect:/patient/details";
    }

    // =========================
    // STEP 2 – PATIENT DETAILS
    // =========================
    @GetMapping("/patient/details")
    public String showPatientForm(HttpSession session) {
        if (session.getAttribute("phone") == null) {
            return "redirect:/patient/register";
        }
        return "patient-register";
    }

    @PostMapping("/patient/details")
    public String savePatientDetails(Patient patient, HttpSession session) {

        patient.setPhone((String) session.getAttribute("phone"));

        // STORE temporarily (DO NOT SAVE TO DB)
        session.setAttribute("patient", patient);

        return "redirect:/patient/confirm";
    }

    // =========================
    // STEP 3 – CONFIRM & BILLING
    // =========================
    @GetMapping("/patient/confirm")
    public String confirmPage(Model model, HttpSession session) {

        Patient patient = (Patient) session.getAttribute("patient");
        if (patient == null) {
            return "redirect:/patient/register";
        }

        // ✅ SAFE TEMP DOCTOR ASSIGNMENT
        Doctor doctor = doctorRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);

        int consultationFee = 450;
        int registrationFee = 50;
        int total = consultationFee + registrationFee;

        model.addAttribute("patient", patient);
        model.addAttribute("doctor", doctor);
        model.addAttribute("consultationFee", consultationFee);
        model.addAttribute("registrationFee", registrationFee);
        model.addAttribute("total", total);

        session.setAttribute("doctor", doctor);

        return "patient-confirm";
    }

    // =========================
    // FINAL – SAVE TO DATABASE
    // =========================
    @PostMapping("/patient/pay")
    public String payAndRegister(HttpSession session) {

        Patient patient = (Patient) session.getAttribute("patient");
        Doctor doctor = (Doctor) session.getAttribute("doctor");

        if (patient == null || doctor == null) {
            return "redirect:/patient/register";
        }

        // ✅ Your Patient entity supports this
        patient.setAssignedDoctorCode(doctor.getDoctorCode());

        // ✅ CORRECT METHOD NAME
        patientService.savePatient(patient);

        session.invalidate();

        return "redirect:/patient/success";
    }
}

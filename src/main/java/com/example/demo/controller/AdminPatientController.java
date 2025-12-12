package com.example.demo.controller;

import com.example.demo.entity.Patient;
import com.example.demo.service.PatientService;
import com.example.demo.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/patients")
public class AdminPatientController {

    private final PatientService patientService;
    private final DoctorService doctorService;

    // ---------------- LIST ALL PATIENTS ----------------
    @GetMapping
    public String listPatients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "admin-patients";
    }

    // ---------------- CREATE PATIENT FORM ----------------
    @GetMapping("/add")
    public String addPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "admin-add-patient";
    }

    // ---------------- SAVE NEW PATIENT ----------------
    @PostMapping("/add")
    public String savePatient(@ModelAttribute Patient patient) {
        patientService.savePatient(patient);
        return "redirect:/admin/patients";
    }

    // ---------------- LOAD EDIT PAGE ----------------
    @GetMapping("/edit/{id}")
    public String editPatientForm(@PathVariable Long id, Model model) {
        model.addAttribute("patient", patientService.getPatient(id));
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "admin-edit-patient";
    }

    // ---------------- UPDATE PATIENT ----------------
    @PostMapping("/update")
    public String updatePatient(@ModelAttribute Patient patient) {
        patientService.savePatient(patient);    // save() works for update also
        return "redirect:/admin/patients";
    }

    // ---------------- DELETE PATIENT ----------------
    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return "redirect:/admin/patients";
    }
}

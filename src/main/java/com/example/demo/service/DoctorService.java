package com.example.demo.service;

import com.example.demo.entity.Doctor;
import com.example.demo.entity.Patient;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    // ----------------------------------------------------
    // BASIC CRUD
    // ----------------------------------------------------

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctor(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public Doctor saveDoctor(Doctor doctor) {
        if (doctor.getDoctorCode() == null || doctor.getDoctorCode().isEmpty()) {
            long nextId = doctorRepository.count() + 1;
            doctor.setDoctorCode("DOC-" + String.format("%03d", nextId));
        }
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    // ----------------------------------------------------
    // AUTH-BASED DOCTOR HELPERS
    // ----------------------------------------------------

    // Get logged-in Doctor object
    public Doctor getLoggedInDoctor() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return doctorRepository.findByEmail(username);
    }

    // Return doctorCode of logged-in doctor
    public String getDoctorCodeForLoggedIn() {
        Doctor d = getLoggedInDoctor();
        return (d != null) ? d.getDoctorCode() : null;
    }

    // Return name of logged-in doctor
    public String getLoggedInDoctorName() {
        Doctor d = getLoggedInDoctor();
        return (d != null) ? d.getName() : "Doctor";
    }

    // ----------------------------------------------------
    // DASHBOARD COUNTS (Placeholders until Appointment entity exists)
    // ----------------------------------------------------

    public long countTodayAppointments(String doctorCode) {
        // You can implement later when Appointment table exists
        return 0;
    }

    public List<?> getTodayAppointments(String doctorCode) {
        // Placeholder list until Appointment entity is created
        return List.of();
    }

    // ----------------------------------------------------
    // PATIENT OPERATIONS (doctorCode-based)
    // ----------------------------------------------------

    public List<Patient> getPatientsByDoctorCode(String doctorCode) {
        return patientRepository.findByAssignedDoctorCode(doctorCode);
    }

    public long countPatientsByDoctorCode(String doctorCode) {
        return patientRepository.countByAssignedDoctorCode(doctorCode);
    }
}

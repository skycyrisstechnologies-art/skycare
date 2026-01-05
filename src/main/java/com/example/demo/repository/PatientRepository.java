package com.example.demo.repository;

import com.example.demo.entity.Doctor;
import com.example.demo.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    // ✅ EXISTING (DO NOT TOUCH)
    List<Patient> findByDoctor(Doctor doctor);
    long countByDoctor(Doctor doctor);

    // ✅ NEW (FOR API / ANALYTICS)
    List<Patient> findByAssignedDoctorCode(String assignedDoctorCode);
    long countByAssignedDoctorCode(String assignedDoctorCode);
}

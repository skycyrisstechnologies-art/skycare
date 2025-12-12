package com.example.demo.repository;

import com.example.demo.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findByDoctorCode(String doctorCode);
    Doctor findByEmail(String email);
}

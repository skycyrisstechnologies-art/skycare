package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ================= BASIC DETAILS =================
    @Column(nullable = false)
    private String name;

    private int age;
    private String gender;
    private String phone;
    private String email;
    private String disease;

    // ================= DOCTOR ASSIGNMENT (RELATION) =================
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    // ================= DOCTOR CODE (FOR API / ANALYTICS) =================
    @Column(name = "assigned_doctor_code")
    private String assignedDoctorCode;

    // ================= AUDIT =================
    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;
}

package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "doctors")
@Getter @Setter
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String doctorCode;

    @Column(unique = true, nullable = false)
    private String username;   // 🔥 NEW

    private String name;
    private String specialization;
    private String wing;
    private String phone;
    private String email;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    public enum Status { ACTIVE, INACTIVE }
}

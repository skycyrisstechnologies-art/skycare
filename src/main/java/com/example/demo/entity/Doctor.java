package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String doctorCode;

    @Column(nullable = false)
    private String name;

    private String specialization;
    private String wing;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    private String phone;
    private String email;

    public enum Status {
        ACTIVE, INACTIVE
    }
}

package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visit_notes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;

    @Column(length = 2000)
    private String note;

    private String doctorCode;

    // ✅ ADD THIS
    private LocalDateTime createdAt;
}

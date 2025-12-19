package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "test_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    private String testName;
    private String status;
    private String doctorCode;

    // ✅ ADD THIS
    private LocalDateTime requestedAt;
}

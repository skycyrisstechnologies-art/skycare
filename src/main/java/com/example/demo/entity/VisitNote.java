package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private String note;
    private String doctorCode;
}

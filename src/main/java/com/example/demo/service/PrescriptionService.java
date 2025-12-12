package com.example.demo.service;

import com.example.demo.entity.Prescription;
import com.example.demo.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository repo;

    public List<Prescription> getPrescriptionsByPatient(Long patientId) {
        return repo.findByPatientIdOrderByIdDesc(patientId);
    }

    public Prescription savePrescription(Prescription p) {
        return repo.save(p);
    }
}

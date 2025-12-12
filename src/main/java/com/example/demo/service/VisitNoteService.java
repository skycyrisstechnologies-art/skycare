package com.example.demo.service;

import com.example.demo.entity.VisitNote;
import com.example.demo.repository.VisitNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitNoteService {

    private final VisitNoteRepository visitNoteRepository;

    // ✔ DoctorController needs this
    public List<VisitNote> getNotesByPatient(Long patientId) {
        return visitNoteRepository.findByPatientId(patientId);
    }

    // ✔ DoctorController needs this
    public VisitNote saveNote(VisitNote note) {
        return visitNoteRepository.save(note);
    }
}

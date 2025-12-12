package com.example.demo.repository;

import com.example.demo.entity.VisitNote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VisitNoteRepository extends JpaRepository<VisitNote, Long> {

    List<VisitNote> findByPatientId(Long patientId);
}

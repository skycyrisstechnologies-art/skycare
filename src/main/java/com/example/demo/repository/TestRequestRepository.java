package com.example.demo.repository;

import com.example.demo.entity.TestRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRequestRepository extends JpaRepository<TestRequest, Long> {

    List<TestRequest> findByPatientId(Long patientId);

    List<TestRequest> findByPatientIdAndStatus(Long patientId, String status);

    long countByDoctorCodeAndStatus(String doctorCode, String status);
}

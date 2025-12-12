package com.example.demo.service;

import com.example.demo.entity.TestRequest;
import com.example.demo.repository.TestRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestRequestService {

    private final TestRequestRepository testRequestRepository;

    public List<TestRequest> getTestsByPatient(Long patientId) {
        return testRequestRepository.findByPatientId(patientId);
    }

    public long countPendingReports(String doctorCode) {
        return testRequestRepository.countByDoctorCodeAndStatus(doctorCode, "PENDING");
    }

    public List<TestRequest> getCompletedReports(Long patientId) {
        return testRequestRepository.findByPatientIdAndStatus(patientId, "COMPLETED");
    }

    public TestRequest saveTestRequest(TestRequest tr) {
        return testRequestRepository.save(tr);
    }
}

package com.example.demo.controller.api;

import com.example.demo.entity.Patient;
import com.example.demo.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@Tag(name = "Patient Registration API", description = "APIs for React integration")
@CrossOrigin(origins = "http://localhost:3000")
public class PatientApiController {

    private final PatientService patientService;

    @Operation(summary = "Register patient (Direct API – React)")
    @PostMapping("/register")
    public ResponseEntity<Patient> registerPatient(
            @RequestBody Patient patient) {

        Patient savedPatient = patientService.savePatient(patient);
        return ResponseEntity.ok(savedPatient);
    }
}

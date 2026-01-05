package com.example.demo.service;

import com.example.demo.entity.Doctor;
import com.example.demo.entity.Patient;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepo;
    private final PatientRepository patientRepo;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    // =====================================================
    // CREATE / UPDATE DOCTOR (ADMIN)
    // =====================================================
    public Doctor saveDoctor(Doctor doctor) {

        // Generate doctorCode ONLY for new doctor
        if (doctor.getId() == null) {
            long next = doctorRepo.count() + 1;
            doctor.setDoctorCode("DOC-" + String.format("%03d", next));
        } else {
            // Preserve existing doctorCode during update
            Doctor existing = doctorRepo.findById(doctor.getId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            doctor.setDoctorCode(existing.getDoctorCode());
        }

        // Username is always email
        doctor.setUsername(doctor.getEmail());

        // Auto-create USER login if not exists
        userRepo.findByUsername(doctor.getUsername())
                .orElseGet(() -> {
                    Role doctorRole = roleRepo.findByName("DOCTOR")
                            .orElseThrow(() -> new RuntimeException("DOCTOR role not found"));

                    User user = new User();
                    user.setUsername(doctor.getUsername());
                    user.setPassword(passwordEncoder.encode("doc@123")); // default password
                    user.setEnabled(true);
                    user.setRoles(Set.of(doctorRole));

                    return userRepo.save(user);
                });

        return doctorRepo.save(doctor);
    }

    // =====================================================
    // BASIC CRUD
    // =====================================================
    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    public Doctor getDoctor(Long id) {
        return doctorRepo.findById(id).orElse(null);
    }

    public void deleteDoctor(Long id) {
        doctorRepo.deleteById(id);
    }

    // =====================================================
    // LOGGED-IN DOCTOR HELPERS
    // =====================================================
    public Doctor getLoggedInDoctor() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return doctorRepo.findByUsername(username);
    }

    public String getDoctorCodeForLoggedIn() {
        Doctor doctor = getLoggedInDoctor();
        return doctor != null ? doctor.getDoctorCode() : null;
    }

    public String getLoggedInDoctorName() {
        Doctor doctor = getLoggedInDoctor();
        return doctor != null ? doctor.getName() : "Doctor";
    }

    // =====================================================
    // PATIENT OPERATIONS (Doctor-wise)
    // =====================================================
    public List<Patient> getPatientsForLoggedInDoctor() {
        String doctorCode = getDoctorCodeForLoggedIn();
        Doctor doctor = getLoggedInDoctor();
return patientRepo.findByDoctor(doctor);

    }

    public long countPatientsForLoggedInDoctor() {
        String doctorCode = getDoctorCodeForLoggedIn();
        Doctor doctor = getLoggedInDoctor();
return patientRepo.countByDoctor(doctor);

    }

    // =====================================================
    // DASHBOARD PLACEHOLDERS (REQUIRED BY DoctorController)
    // =====================================================
    public long countTodayAppointments(String doctorCode) {
        // Appointment module not implemented yet
        return 0;
    }

    public List<?> getTodayAppointments(String doctorCode) {
        // Appointment module not implemented yet
        return List.of();
    }
    // =====================================================
// TEMP – ASSIGN DOCTOR DURING PATIENT REGISTRATION
// =====================================================
public Doctor findLeastBusyDoctor() {
    // Temporary logic: return first available doctor
    return doctorRepo.findAll()
            .stream()
            .findFirst()
            .orElse(null);
}

}

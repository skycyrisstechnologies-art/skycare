package com.example.demo.controller;

import com.example.demo.entity.Patient;
import com.example.demo.entity.Prescription;
import com.example.demo.entity.TestRequest;
import com.example.demo.entity.VisitNote;
import com.example.demo.service.DoctorService;
import com.example.demo.service.PatientService;
import com.example.demo.service.PrescriptionService;
import com.example.demo.service.TestRequestService;
import com.example.demo.service.VisitNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final VisitNoteService visitNoteService;
    private final PrescriptionService prescriptionService;
    private final TestRequestService testRequestService;

    // =======================
    // DOCTOR DASHBOARD
    // =======================
    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        String doctorCode = doctorService.getDoctorCodeForLoggedIn();

        long patientCount = patientService.countByDoctorCode(doctorCode);
        long todayAppointments = doctorService.countTodayAppointments(doctorCode);
        long pendingReports = testRequestService.countPendingReports(doctorCode);

        model.addAttribute("doctorName", doctorService.getLoggedInDoctorName());
        model.addAttribute("patientCount", patientCount);
        model.addAttribute("appointmentCount", todayAppointments);
        model.addAttribute("pendingReports", pendingReports);

        model.addAttribute("appointments", doctorService.getTodayAppointments(doctorCode));

        return "doctor-dashboard";
    }

    // =======================
    // DOCTOR — MY PATIENTS
    // =======================
    @GetMapping("/patients")
    public String myPatients(Model model) {

        String doctorCode = doctorService.getDoctorCodeForLoggedIn();

        List<Patient> patients = patientService.getPatientsByDoctorCode(doctorCode);
        model.addAttribute("patients", patients);

        return "doctor-patients";
    }

    // =======================
    // PATIENT DETAILS PAGE
    // =======================
    @GetMapping("/patient/{id}")
    public String patientDetails(@PathVariable Long id, Model model) {

        Patient patient = patientService.getPatient(id);
        if (patient == null) {
            return "redirect:/doctor/patients";
        }

        model.addAttribute("patient", patient);
        model.addAttribute("notes", visitNoteService.getNotesByPatient(id));
        model.addAttribute("prescriptions", prescriptionService.getPrescriptionsByPatient(id));
        model.addAttribute("tests", testRequestService.getTestsByPatient(id));
        model.addAttribute("completedReports", testRequestService.getCompletedReports(id));

        return "doctor-patient-details";
    }

    // =======================
    // ADD VISIT NOTE
    // =======================
    @PostMapping("/patient/{id}/notes")
    public String addNote(@PathVariable Long id,
                          @RequestParam String note) {

        VisitNote vn = new VisitNote();
        vn.setPatientId(id);
        vn.setNote(note);
        vn.setDoctorCode(doctorService.getDoctorCodeForLoggedIn());

        visitNoteService.saveNote(vn);

        return "redirect:/doctor/patient/" + id;
    }

    // =======================
    // ADD PRESCRIPTION
    // =======================
    @PostMapping("/patient/{id}/prescriptions")
    public String addPrescription(@PathVariable Long id,
                                  @RequestParam String medicine,
                                  @RequestParam String dosage) {

        Prescription p = new Prescription();
        p.setPatientId(id);
        p.setMedicine(medicine);
        p.setDosage(dosage);
        p.setDoctorCode(doctorService.getDoctorCodeForLoggedIn());

        prescriptionService.savePrescription(p);

        return "redirect:/doctor/patient/" + id;
    }

    // =======================
    // REQUEST A TEST
    // =======================
    @PostMapping("/patient/{id}/tests")
    public String requestTest(@PathVariable Long id,
                              @RequestParam String testName) {

        TestRequest tr = new TestRequest();
        tr.setPatientId(id);
        tr.setTestName(testName);
        tr.setStatus("PENDING");
        tr.setDoctorCode(doctorService.getDoctorCodeForLoggedIn());

        testRequestService.saveTestRequest(tr);

        return "redirect:/doctor/patient/" + id;
    }
}

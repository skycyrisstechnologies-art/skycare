
package com.example.demo.dto;

public class AppointmentDTO {

    private String patientName;
    private String time;
    private String reason;
    private String status;

    public AppointmentDTO(String patientName, String time, String reason, String status) {
        this.patientName = patientName;
        this.time = time;
        this.reason = reason;
        this.status = status;
    }

    // Getters and Setters
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

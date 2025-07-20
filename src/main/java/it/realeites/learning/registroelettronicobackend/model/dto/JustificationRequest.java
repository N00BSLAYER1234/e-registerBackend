package it.realeites.learning.registroelettronicobackend.model.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "JustificationRequest", description = "Request to submit justification")
public class JustificationRequest {
    
    @Schema(description = "Presence ID")
    private Integer presenceId;
    
    @Schema(description = "Date of absence")
    private LocalDate date;
    
    @Schema(description = "Reason for absence")
    private String reason;
    
    @Schema(description = "Student ID")
    private Integer studentId;

    public JustificationRequest() {
    }

    public Integer getPresenceId() {
        return presenceId;
    }

    public void setPresenceId(Integer presenceId) {
        this.presenceId = presenceId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
}
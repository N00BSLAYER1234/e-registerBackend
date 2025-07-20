package it.realeites.learning.registroelettronicobackend.model.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AttendanceRequest", description = "Request to mark attendance")
public class AttendanceRequest {
    
    @Schema(description = "Student ID")
    private Integer studentId;
    
    @Schema(description = "Date of attendance")
    private LocalDate date;
    
    @Schema(description = "Month ID")
    private Integer monthId;

    public AttendanceRequest() {
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getMonthId() {
        return monthId;
    }

    public void setMonthId(Integer monthId) {
        this.monthId = monthId;
    }
}
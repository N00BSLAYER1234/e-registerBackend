package it.realeites.learning.registroelettronicobackend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.realeites.learning.registroelettronicobackend.model.dto.ApprovalRequest;
import it.realeites.learning.registroelettronicobackend.model.dto.PresenzaDTO;
import it.realeites.learning.registroelettronicobackend.model.dto.UtenteDTO;
import it.realeites.learning.registroelettronicobackend.model.entity.Ruolo;
import it.realeites.learning.registroelettronicobackend.service.CsvService;
import it.realeites.learning.registroelettronicobackend.service.GiustificativoService;
import it.realeites.learning.registroelettronicobackend.service.PresenzaService;
import it.realeites.learning.registroelettronicobackend.service.UtenteService;

@RestController
@RequestMapping("/api/tutor")
@CrossOrigin(origins = "*")
@Tag(name = "Tutor", description = "Tutor endpoints")
public class TutorController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private PresenzaService presenzaService;

    @Autowired
    private GiustificativoService giustificativoService;

    @Autowired
    private CsvService csvService;

    @GetMapping("/students/{tutorId}")
    @Operation(summary = "Get tutor's students", description = "Get all students assigned to a tutor")
    public ResponseEntity<List<UtenteDTO>> getStudents(@PathVariable Integer tutorId) {
        try {
            List<UtenteDTO> students = utenteService.getTutoratiByTutor(tutorId);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/students")
    @Operation(summary = "Get all students", description = "Get all students in the system")
    public ResponseEntity<List<UtenteDTO>> getAllStudents() {
        try {
            List<UtenteDTO> students = utenteService.findByRuolo(Ruolo.TUTORATO);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/attendance/{studentId}")
    @Operation(summary = "Get student attendance", description = "Get attendance records for a specific student")
    public ResponseEntity<List<PresenzaDTO>> getStudentAttendance(@PathVariable Integer studentId) {
        try {
            List<PresenzaDTO> attendance = presenzaService.findAllByUtente_Id(studentId);
            return ResponseEntity.ok(attendance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/attendance/month/{monthId}")
    @Operation(summary = "Get attendance by month", description = "Get all attendance records for a specific month")
    public ResponseEntity<List<PresenzaDTO>> getAttendanceByMonth(@PathVariable Integer monthId) {
        try {
            List<PresenzaDTO> attendance = presenzaService.findAllByMese_Id(monthId);
            return ResponseEntity.ok(attendance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/approve/{presenceId}")
    @Operation(summary = "Approve attendance", description = "Approve a student's attendance record")
    public ResponseEntity<String> approveAttendance(@PathVariable Integer presenceId, @RequestBody ApprovalRequest request) {
        try {
            presenzaService.setApprovato(presenceId, request.getTutorId());
            return ResponseEntity.ok("Attendance approved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/disapprove/{presenceId}")
    @Operation(summary = "Disapprove attendance", description = "Disapprove a student's attendance record")
    public ResponseEntity<String> disapproveAttendance(@PathVariable Integer presenceId) {
        try {
            presenzaService.deleteApprovato(presenceId);
            return ResponseEntity.ok("Attendance disapproved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/stats/{tutorId}")
    @Operation(summary = "Get tutor statistics", description = "Get statistics for all students under a tutor")
    public ResponseEntity<TutorStats> getStats(@PathVariable Integer tutorId) {
        try {
            List<UtenteDTO> students = utenteService.getTutoratiByTutor(tutorId);
            
            long totalStudents = students.size();
            long totalAttendanceRecords = 0;
            long totalPresentRecords = 0;
            long pendingJustifications = 0;
            
            for (UtenteDTO student : students) {
                List<PresenzaDTO> attendance = presenzaService.findAllByUtente_Id(student.getId());
                totalAttendanceRecords += attendance.size();
                totalPresentRecords += attendance.stream().filter(p -> p.getStato()).count();
                
                // Count pending justifications (justifications that exist but are not approved)
                pendingJustifications += attendance.stream()
                    .filter(p -> !p.getStato() && p.getGiustificativo() != null && !p.isApprovato())
                    .count();
            }
            
            double averageAttendance = totalAttendanceRecords > 0 ? (double) totalPresentRecords / totalAttendanceRecords * 100 : 0;
            
            TutorStats stats = new TutorStats();
            stats.setTotalStudents(totalStudents);
            stats.setAverageAttendance(averageAttendance);
            stats.setPendingJustifications(pendingJustifications);
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/export/{tutorId}")
    @Operation(summary = "Export attendance data", description = "Export attendance data as CSV")
    public ResponseEntity<String> exportData(@PathVariable Integer tutorId) {
        try {
            List<UtenteDTO> students = utenteService.getTutoratiByTutor(tutorId);
            
            // Get all attendance records for the tutor's students
            StringBuilder csvData = new StringBuilder();
            csvData.append("Student Name,Date,Status,Approved,Justification\n");
            
            for (UtenteDTO student : students) {
                List<PresenzaDTO> attendance = presenzaService.findAllByUtente_Id(student.getId());
                
                for (PresenzaDTO presence : attendance) {
                    csvData.append(String.format("%s %s,%s,%s,%s,%s\n",
                        student.getNome(),
                        student.getCognome(),
                        presence.getData().toString(),
                        presence.getStato() ? "Present" : "Absent",
                        presence.isApprovato() ? "Yes" : "No",
                        presence.getGiustificativo() != null ? presence.getGiustificativo().getDescrizione() : ""
                    ));
                }
            }
            
            return ResponseEntity.ok(csvData.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    // Inner class for statistics
    public static class TutorStats {
        private long totalStudents;
        private double averageAttendance;
        private long pendingJustifications;

        public long getTotalStudents() {
            return totalStudents;
        }

        public void setTotalStudents(long totalStudents) {
            this.totalStudents = totalStudents;
        }

        public double getAverageAttendance() {
            return averageAttendance;
        }

        public void setAverageAttendance(double averageAttendance) {
            this.averageAttendance = averageAttendance;
        }

        public long getPendingJustifications() {
            return pendingJustifications;
        }

        public void setPendingJustifications(long pendingJustifications) {
            this.pendingJustifications = pendingJustifications;
        }
    }
}
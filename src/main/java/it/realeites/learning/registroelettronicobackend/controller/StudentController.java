package it.realeites.learning.registroelettronicobackend.controller;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.realeites.learning.registroelettronicobackend.model.dto.AttendanceRequest;
import it.realeites.learning.registroelettronicobackend.model.dto.JustificationRequest;
import it.realeites.learning.registroelettronicobackend.model.dto.PresenzaDTO;
import it.realeites.learning.registroelettronicobackend.model.dto.PresenzaRequest;
import it.realeites.learning.registroelettronicobackend.model.dto.UtenteDTO;
import it.realeites.learning.registroelettronicobackend.service.GiustificativoService;
import it.realeites.learning.registroelettronicobackend.service.MeseService;
import it.realeites.learning.registroelettronicobackend.service.PresenzaService;
import it.realeites.learning.registroelettronicobackend.service.UtenteService;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*")
@Tag(name = "Student", description = "Student endpoints")
public class StudentController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private PresenzaService presenzaService;

    @Autowired
    private GiustificativoService giustificativoService;

    @Autowired
    private MeseService meseService;

    @GetMapping("/profile/{id}")
    @Operation(summary = "Get student profile", description = "Get student profile by ID")
    public ResponseEntity<UtenteDTO> getProfile(@PathVariable Integer id) {
        try {
            Optional<UtenteDTO> student = utenteService.findById(id);
            
            if (student.isPresent()) {
                return ResponseEntity.ok(student.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/attendance")
    @Operation(summary = "Mark attendance", description = "Mark student attendance for today")
    public ResponseEntity<String> markAttendance(@RequestBody AttendanceRequest request) {
        try {
            // Create presence request
            PresenzaRequest presenzaRequest = new PresenzaRequest();
            presenzaRequest.setData(request.getDate() != null ? request.getDate() : LocalDate.now());
            presenzaRequest.setStato(true); // Present
            
            // Set user
            Optional<UtenteDTO> student = utenteService.findById(request.getStudentId());
            if (student.isPresent()) {
                presenzaRequest.setUtente(student.get());
            }
            
            presenzaRequest.setIdMese(request.getMonthId());

            PresenzaDTO presenza = presenzaService.save(presenzaRequest);
            
            if (presenza != null) {
                return ResponseEntity.ok("Attendance marked successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to mark attendance");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/attendance/{studentId}")
    @Operation(summary = "Get student attendance", description = "Get attendance for a specific student")
    public ResponseEntity<List<PresenzaDTO>> getAttendance(@PathVariable Integer studentId) {
        try {
            List<PresenzaDTO> attendance = presenzaService.findAllByUtente_Id(studentId);
            return ResponseEntity.ok(attendance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/attendance/{studentId}/month/{monthId}")
    @Operation(summary = "Get student attendance by month", description = "Get attendance for a specific student in a specific month")
    public ResponseEntity<List<PresenzaDTO>> getAttendanceByMonth(@PathVariable Integer studentId, @PathVariable Integer monthId) {
        try {
            List<PresenzaDTO> attendance = presenzaService.findAllByMese_Id(monthId);
            // Filter by student ID
            List<PresenzaDTO> studentAttendance = attendance.stream()
                .filter(p -> p.getUtente().getId().equals(studentId))
                .toList();
            return ResponseEntity.ok(studentAttendance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/justification")
    @Operation(summary = "Submit justification", description = "Submit a justification for absence")
    public ResponseEntity<String> submitJustification(@RequestBody JustificationRequest request) {
        try {
            // Add justification to attendance record
            presenzaService.addGiustificativo(request.getPresenceId(), request.getReason());
            
            return ResponseEntity.ok("Justification submitted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/stats/{studentId}")
    @Operation(summary = "Get student statistics", description = "Get attendance statistics for a student")
    public ResponseEntity<StudentStats> getStats(@PathVariable Integer studentId, @RequestParam(required = false) Integer month, @RequestParam(required = false) Integer year) {
        try {
            List<PresenzaDTO> attendance = presenzaService.findAllByUtente_Id(studentId);
            
            // Filter by month/year if provided
            if (month != null && year != null) {
                attendance = attendance.stream()
                    .filter(p -> p.getData().getMonthValue() == month && p.getData().getYear() == year)
                    .toList();
            }
            
            long totalDays = attendance.size();
            long presentDays = attendance.stream().filter(p -> p.getStato()).count();
            long absentDays = totalDays - presentDays;
            double attendanceRate = totalDays > 0 ? (double) presentDays / totalDays * 100 : 0;
            
            StudentStats stats = new StudentStats();
            stats.setTotalDays(totalDays);
            stats.setPresentDays(presentDays);
            stats.setAbsentDays(absentDays);
            stats.setAttendanceRate(attendanceRate);
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Inner class for statistics
    public static class StudentStats {
        private long totalDays;
        private long presentDays;
        private long absentDays;
        private double attendanceRate;

        public long getTotalDays() {
            return totalDays;
        }

        public void setTotalDays(long totalDays) {
            this.totalDays = totalDays;
        }

        public long getPresentDays() {
            return presentDays;
        }

        public void setPresentDays(long presentDays) {
            this.presentDays = presentDays;
        }

        public long getAbsentDays() {
            return absentDays;
        }

        public void setAbsentDays(long absentDays) {
            this.absentDays = absentDays;
        }

        public double getAttendanceRate() {
            return attendanceRate;
        }

        public void setAttendanceRate(double attendanceRate) {
            this.attendanceRate = attendanceRate;
        }
    }
}
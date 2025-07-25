package com.ehrapp.ehr_backend.controller;

import com.ehrapp.ehr_backend.dto.ReportDTO;
import com.ehrapp.ehr_backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadReport(@RequestParam("file") MultipartFile file,
                                          @RequestParam("patientId") Long patientId,
                                          @RequestParam("uploadedById") Long uploadedById) {
        try {
            ReportDTO report = reportService.uploadReport(patientId, uploadedById, file);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable Long id) {
        ReportDTO report = reportService.getReport(id);
        if (report == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(report);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<ReportDTO>> getReportsByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(reportService.getReportsByPatient(patientId));
    }
}

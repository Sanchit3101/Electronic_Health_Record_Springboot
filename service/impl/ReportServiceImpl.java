package com.ehrapp.ehr_backend.service.impl;

import com.ehrapp.ehr_backend.dto.EmailRequest;
import com.ehrapp.ehr_backend.dto.ReportDTO;
import com.ehrapp.ehr_backend.entity.Patient;
import com.ehrapp.ehr_backend.entity.ReportFile;
import com.ehrapp.ehr_backend.entity.User;
import com.ehrapp.ehr_backend.repository.PatientRepository;
import com.ehrapp.ehr_backend.repository.ReportFileRepository;
import com.ehrapp.ehr_backend.repository.UserRepository;
import com.ehrapp.ehr_backend.s3.S3Service;
import com.ehrapp.ehr_backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class
ReportServiceImpl implements ReportService {

    @Autowired
    private ReportFileRepository reportFileRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private RestTemplate restTemplate;

    private static final String MAIL_SERVICE_URL = "http://localhost:8081/api/mail/send";

    @Override
    public ReportDTO uploadReport(Long patientId, Long uploadedById, MultipartFile file) throws IOException {
        try {
            // 1. Upload to S3
            String fileUrl = s3Service.uploadFile(file);

            // 2. Validate patient
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new RuntimeException("❌ Patient with ID " + patientId + " not found."));

            // 3. Validate uploader
            User uploader = userRepository.findById(uploadedById)
                    .orElseThrow(() -> new RuntimeException("❌ Uploader with ID " + uploadedById + " not found."));

            // 4. Save report metadata
            ReportFile report = new ReportFile();
            report.setPatient(patient);
            report.setUploadedBy(uploader);
            report.setFileType(file.getContentType() != null ? file.getContentType() : "application/octet-stream");
            report.setFileUrl(fileUrl);
            report.setUploadedAt(LocalDateTime.now());

            reportFileRepository.save(report);

            // 5. Send email via external mail microservice
            String patientEmail = patient.getUser().getEmail();
            String uploaderEmail = uploader.getEmail();

            String subject = "📄 New Report Uploaded";
            String messageToPatient = "Dear " + patient.getName() + ",\n\nA new medical report has been uploaded to your EHR dashboard.\n\nRegards,\nEHR System";
            String messageToDoctor = "A new report has been uploaded by patient: " + patient.getName();

            if (!uploader.getId().equals(patient.getUser().getId())) {
                // Doctor uploaded the report
                restTemplate.postForObject(MAIL_SERVICE_URL, new EmailRequest(patientEmail, subject, messageToPatient), String.class);
                restTemplate.postForObject(MAIL_SERVICE_URL, new EmailRequest(uploaderEmail, subject, messageToDoctor), String.class);
            } else {
                // Patient uploaded the report
                restTemplate.postForObject(MAIL_SERVICE_URL,
                        new EmailRequest("doctor@example.com", "📄 Report Uploaded by Patient",
                                "Patient " + patient.getName() + " uploaded a report."),
                        String.class);
            }

            // 6. Return DTO
            return mapToDTO(report);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("❌ Upload failed: " + e.getMessage());
        }
    }

    @Override
    public ReportDTO getReport(Long reportId) {
        return reportFileRepository.findById(reportId)
                .map(this::mapToDTO)
                .orElse(null);
    }

    @Override
    public List<ReportDTO> getReportsByPatient(Long patientId) {
        return reportFileRepository.findByPatientId(patientId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ReportDTO mapToDTO(ReportFile report) {
        ReportDTO dto = new ReportDTO();
        dto.setId(report.getId());
        dto.setPatientId(report.getPatient().getId());
        dto.setUploadedById(report.getUploadedBy().getId());
        dto.setFileUrl(report.getFileUrl());
        dto.setFileType(report.getFileType());
        dto.setUploadedAt(report.getUploadedAt());
        return dto;
    }
}

package com.ehrapp.ehr_backend.service;

import com.ehrapp.ehr_backend.dto.ReportDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ReportService {
    ReportDTO uploadReport(Long patientId, Long uploadedById, MultipartFile file) throws IOException;

    ReportDTO getReport(Long reportId);

    List<ReportDTO> getReportsByPatient(Long patientId);
}


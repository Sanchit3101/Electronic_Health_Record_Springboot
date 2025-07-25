package com.ehrapp.ehr_backend.repository;

import com.ehrapp.ehr_backend.entity.ReportFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportFileRepository extends JpaRepository<ReportFile,Long>{
    List<ReportFile> findByPatientId(Long patientId);
}

package com.ehrapp.ehr_backend.repository;

import com.ehrapp.ehr_backend.entity.PatientLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientLogRepository extends JpaRepository<PatientLog, Long> {
}

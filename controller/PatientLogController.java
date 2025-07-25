package com.ehrapp.ehr_backend.controller;

import com.ehrapp.ehr_backend.entity.PatientLog;
import com.ehrapp.ehr_backend.repository.PatientLogRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
@CrossOrigin(origins = "*") // Allow frontend access
public class PatientLogController {

    private final PatientLogRepository logRepository;

    public PatientLogController(PatientLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @GetMapping
    public List<PatientLog> getAllLogs() {
        return logRepository.findAll();
    }
}

package com.ehrapp.ehr_backend.service;

import com.ehrapp.ehr_backend.entity.PatientLog;
import com.ehrapp.ehr_backend.repository.PatientLogRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class KafkaConsumerService {

    private final PatientLogRepository logRepository;

    public KafkaConsumerService(PatientLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @KafkaListener(topics = "${kafka.topic.patient-log}", groupId = "log-group")
    public void consume(String message) {
        PatientLog log = new PatientLog(message, LocalDateTime.now());
        logRepository.save(log);
        System.out.println("Consumed message: " + message);
    }
}

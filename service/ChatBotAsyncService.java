package com.ehrapp.ehr_backend.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

@Service
public class ChatBotAsyncService {

    private final RestTemplate restTemplate;

    public ChatBotAsyncService() {
        this.restTemplate = new RestTemplate(); // manually initializing
    }

    @Async
    public CompletableFuture<String> getReplyAsync(String question) {
        try {
            String chatbotUrl = "http://localhost:8082/api/chat/ask?question=" +
                    UriUtils.encode(question, StandardCharsets.UTF_8);

            String response = restTemplate.postForObject(chatbotUrl, null, String.class);
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            return CompletableFuture.completedFuture("Chatbot failed: " + e.getMessage());
        }
    }
}

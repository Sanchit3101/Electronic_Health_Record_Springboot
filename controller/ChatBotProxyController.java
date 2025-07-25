package com.ehrapp.ehr_backend.controller;

import com.ehrapp.ehr_backend.dto.ChatRequestDTO;
import com.ehrapp.ehr_backend.service.ChatBotAsyncService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin(origins = "*")
public class ChatBotProxyController {

    private final ChatBotAsyncService chatBotAsyncService;

    // ✅ Manual constructor to initialize final field
    public ChatBotProxyController(ChatBotAsyncService chatBotAsyncService) {
        this.chatBotAsyncService = chatBotAsyncService;
    }

    @PostMapping("/ask")
    public ResponseEntity<String> askAsync(@RequestBody ChatRequestDTO dto) {
        chatBotAsyncService.getReplyAsync(dto.getQuestion());
        return ResponseEntity.accepted().body("Bot is thinking... Please wait.");
    }
}

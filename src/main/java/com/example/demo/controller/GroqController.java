package com.example.demo.controller;

import com.example.demo.service.GroqService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class GroqController {

    private final GroqService groqService;

    public GroqController(GroqService groqService) {
        this.groqService = groqService;
    }

    @PostMapping
    public Map<String, String> chat(@RequestBody Map<String, String> payload) {
        String reply = groqService.ask(payload.get("message"));
        return Map.of("reply", reply);
    }
}
    
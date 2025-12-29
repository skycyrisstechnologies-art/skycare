package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GroqService {

    private final WebClient webClient;

    public GroqService(
            WebClient.Builder builder,
            @Value("${groq.api.key}") String apiKey) {

        this.webClient = builder
                .baseUrl("https://api.groq.com/openai/v1")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        System.out.println("✅ GROQ SERVICE LOADED");
    }

    public String ask(String userMessage) {

        Map<String, Object> body = new HashMap<>();

        body.put("model", "llama-3.1-8b-instant");

        body.put("messages", List.of(

            // 🔒 SYSTEM PROMPT (THIS CONTROLS BEHAVIOR)
            Map.of(
                "role", "system",
                "content",
                "You are SkyCare Hospital Assistant. " +
                "Only answer questions related to hospitals, doctors, patients, medicines, appointments, health, or medical care. " +
                "Keep answers very short (maximum 2–3 lines). " +
                "If the question is not related to hospital or healthcare, reply: " +
                "'I can only help with hospital-related questions.'"
            ),

            // USER MESSAGE
            Map.of(
                "role", "user",
                "content", userMessage
            )
        ));

        body.put("max_tokens", 80);      // 🔥 SHORT answers
        body.put("temperature", 0.2);    // 🔥 Less creativity, more accuracy

        try {
            Map response = webClient.post()
                    .uri("/chat/completions")
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            List<Map> choices = (List<Map>) response.get("choices");
            Map message = (Map) choices.get(0).get("message");

            return message.get("content").toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Sorry, I can only help with hospital-related questions.";
        }
    }
}

package com.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    // 🔥 Config-based switch (BEST PRACTICE)
    @Value("${ai.mode}")
    private String mode;

    private final String URL = "https://api.openai.com/v1/chat/completions";

    // ==============================
    // 🔹 RESUME ANALYSIS
    // ==============================
    public String analyzeResume(String resumeText) {

        // ✅ Dummy mode
        if ("dummy".equals(mode)) {
            return "Score: 8/10. Good resume, improve project descriptions and add more achievements.";
        }

        // 🔽 Real API
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4o-mini");

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system",
                "content", "You are an expert HR. Analyze resume and give score out of 10 with improvements."));
        messages.add(Map.of("role", "user", "content", resumeText));

        body.put("messages", messages);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        Map response = restTemplate.postForObject(URL, entity, Map.class);

        List choices = (List) response.get("choices");
        Map choice = (Map) choices.get(0);
        Map message = (Map) choice.get("message");

        return message.get("content").toString();
    }

    // ==============================
    // 🔹 GENERATE QUESTIONS
    // ==============================
    public List<String> generateQuestions(String domain, String difficulty) {

        // ✅ Dummy mode
        if ("dummy".equals(mode)) {
            return List.of(
                    "What is " + domain + "?",
                    "Explain OOP concepts in " + domain,
                    "What is JVM?",
                    "Difference between JDK and JRE",
                    "What is multithreading in " + domain
            );
        }

        // 🔽 Real API
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4o-mini");

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system",
                "content", "You are a technical interviewer. Generate 5 interview questions."));
        messages.add(Map.of("role", "user",
                "content", "Generate 5 " + difficulty + " level interview questions on " + domain));

        body.put("messages", messages);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        Map response = restTemplate.postForObject(URL, entity, Map.class);

        List choices = (List) response.get("choices");
        Map choice = (Map) choices.get(0);
        Map message = (Map) choice.get("message");

        String content = message.get("content").toString();

        return Arrays.asList(content.split("\n"));
    }

    // ==============================
    // 🔹 EVALUATE ANSWER
    // ==============================
    public String evaluateAnswer(String question, String answer) {

        // ✅ Dummy mode
        if ("dummy".equals(mode)) {
            return "Score: 7/10. Good answer, but improve explanation and add examples.";
        }

        // 🔽 Real API
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4o-mini");

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system",
                "content", "You are a technical interviewer. Evaluate answer and give score out of 10 and feedback."));
        messages.add(Map.of("role", "user",
                "content", "Question: " + question + "\nAnswer: " + answer));

        body.put("messages", messages);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        Map response = restTemplate.postForObject(URL, entity, Map.class);

        List choices = (List) response.get("choices");
        Map choice = (Map) choices.get(0);
        Map message = (Map) choice.get("message");

        return message.get("content").toString();
    }
}
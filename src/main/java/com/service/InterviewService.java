package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays; // Added
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.entity.*;
import com.repository.*;

@Service
public class InterviewService {

    @Autowired
    private InterviewRepository interviewRepo;

    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private GeminiService geminiService; // Added this

    public Interview startInterview(Long userId, String domain, String difficulty) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 1. Save Interview
        Interview interview = new Interview();
        interview.setUser(user);
        interview.setDomain(domain);
        interview.setDifficulty(difficulty);
        Interview savedInterview = interviewRepo.save(interview);

        // 2. Generate Questions using Gemini
     // Inside InterviewService.java
        String prompt = "Generate 5 interview questions for a " + domain + " role at " + difficulty + " level. " +
                        "Return only the questions separated by a '|' character.";
        String aiResponse = geminiService.getAIResponse(prompt);
        List<String> generatedQuestions = Arrays.asList(aiResponse.split("\\|"));
        
        // 3. Save Questions
        List<Question> questions = new ArrayList<>();
        for (String qText : generatedQuestions) {
            Question question = new Question();
            question.setQuestionText(qText.trim());
            question.setInterview(savedInterview);
            questions.add(question);
        }

        questionRepo.saveAll(questions);
        return savedInterview;
    }
}
package com.service;

import com.data_authenticate.AnswerRequest;
import com.entity.Answer;
import com.entity.Question;
import com.repository.AnswerRepository;
import com.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepo;

    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private GeminiService geminiService; // Added this

    public Answer submitAnswer(AnswerRequest request) {

        Question question = questionRepo.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // AI Evaluation Prompt
     // Inside AnswerService.java
        String prompt = "Question: " + question.getQuestionText() + "\nUser Answer: " + request.getAnswer() + 
                        "\nRate this answer out of 10 and give feedback.";
        String evaluation = geminiService.getAIResponse(prompt);
        // Use your extractScore logic to parse the number from 'evaluation'
        
        // Extracting score and feedback from AI string
        int score = extractScore(evaluation);

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setAnswerText(request.getAnswer());
        answer.setFeedback(evaluation); // Stores the full AI feedback
        answer.setScore(score);

        return answerRepo.save(answer);
    }

    private int extractScore(String text) {
        try {
            // Looks for the first number in the AI response
            String scoreStr = text.replaceAll("[^0-9]", " ").trim().split(" ")[0];
            return Integer.parseInt(scoreStr);
        } catch (Exception e) {
            return 5; // Default fallback score
        }
    }
}
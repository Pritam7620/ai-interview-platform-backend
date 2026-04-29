package com.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Interview;
import com.entity.Question;
import com.entity.User;
import com.repository.InterviewRepository;
import com.repository.QuestionRepository;
import com.repository.UserRepo;

@Service
public class InterviewService {

    @Autowired
    private InterviewRepository interviewRepo;

    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OpenAIService openAIService;

    public Interview startInterview(Long userId, String domain, String difficulty) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 1. Save Interview
        Interview interview = new Interview();
        interview.setUser(user);
        interview.setDomain(domain);
        interview.setDifficulty(difficulty);

        Interview savedInterview = interviewRepo.save(interview);

        // 2. Generate Questions (AI)
        List<String> generatedQuestions = List.of(
        	    "What is Java?",
        	    "Explain OOP concepts",
        	    "What is inheritance?",
        	    "What is polymorphism?"
        	);
        // 3. Save Questions
        List<Question> questions = new ArrayList();

        for (String q : generatedQuestions) {
            Question question = new Question();
            question.setQuestionText(q);
            question.setInterview(savedInterview);
            questions.add(question);
        }

        questionRepo.saveAll(questions);

        return savedInterview;
    }
}

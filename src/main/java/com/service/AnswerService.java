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
    private OpenAIService openAIService;

    public Answer submitAnswer(AnswerRequest request) {

        Question question = questionRepo.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        String evaluation = "Good answer, but can be improved with more details.";
        int score = 7;

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setAnswerText(request.getAnswer());
        answer.setFeedback(evaluation);
        answer.setScore(score);

        return answerRepo.save(answer);
    }

    private int extractScore(String text) {
        try {
            return Integer.parseInt(text.replaceAll("[^0-9]", "").substring(0, 2));
        } catch (Exception e) {
            return 5; // fallback
        }
    }
}
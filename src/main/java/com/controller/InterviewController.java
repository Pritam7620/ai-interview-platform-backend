package com.controller;

import com.data_authenticate.InterviewRequest;
import com.entity.Interview;
import com.entity.Question;
import com.repository.QuestionRepository;
import com.service.InterviewService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interview")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;
@Autowired
    private QuestionRepository questionRepository; 
    @PostMapping("/start")
    public Map<String, Object> startInterview(@RequestBody InterviewRequest request) {

        Interview interview = interviewService.startInterview(
                request.getUserId(),
                request.getDomain(),
                request.getDifficulty()
        );

        List<Question> questions = questionRepository.findByInterview(interview);

        Map<String, Object> response = new HashMap<>();
        response.put("interview", interview);
        response.put("questions", questions);

        return response;
    }
}
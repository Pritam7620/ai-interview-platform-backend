package com.controller;


import com.data_authenticate.AnswerRequest;
import com.entity.Answer;
import com.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping("/submit")
    public Answer submitAnswer(@RequestBody AnswerRequest request) {
        return answerService.submitAnswer(request);
    }
}

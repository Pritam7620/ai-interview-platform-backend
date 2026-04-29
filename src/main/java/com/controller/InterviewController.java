package com.controller;

import com.data_authenticate.InterviewRequest;
import com.entity.Interview;
import com.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interview")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @PostMapping("/start")
    public Interview startInterview(@RequestBody InterviewRequest request) {
        return interviewService.startInterview(
                request.getUserId(),
                request.getDomain(),
                request.getDifficulty()
        );
    }
}
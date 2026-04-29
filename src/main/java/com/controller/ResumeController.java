package com.controller;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.service.GeminiService; // Service name update kiya

@RestController
@RequestMapping("/resume")
public class ResumeController {

    @Autowired
    private GeminiService geminiService; // GeminiService use karein
    
    @PostMapping("/upload")
    public String uploadResume(@RequestParam("file") MultipartFile file) {

        try {
            // PDF load aur text extract karna
            PDDocument document = PDDocument.load(file.getInputStream());
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            document.close();

            // AI ke liye prompt taiyar karein
            String prompt = "Analyze this resume text and provide a summary of skills, " +
                            "suggested job roles, and potential interview topics: \n" + text;

            // Gemini service ko call karein[cite: 1]
            String aiResponse = geminiService.getAIResponse(prompt);

            return aiResponse;

        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }
    }
}
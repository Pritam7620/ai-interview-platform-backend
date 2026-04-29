package com.controller;


import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.service.OpenAIService;

@RestController
@RequestMapping("/resume")
public class ResumeController {

	@Autowired
	private OpenAIService openAIService;
	
	@PostMapping("/upload")
	public String uploadResume(@RequestParam("file") MultipartFile file) {

	    try {
	        PDDocument document = PDDocument.load(file.getInputStream());
	        PDFTextStripper pdfStripper = new PDFTextStripper();

	        String text = pdfStripper.getText(document);
	        document.close();

	        //call ai here 
	        String aiResponse = openAIService.analyzeResume(text);

	        return aiResponse;

	    } catch (IOException e) {
	        return "Error reading file";
	    }
	}
}
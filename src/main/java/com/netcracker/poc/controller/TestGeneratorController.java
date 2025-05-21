package com.netcracker.poc.controller;

import com.netcracker.poc.service.TestGenerationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tests")
public class TestGeneratorController {

    private final TestGenerationService testService;

    public TestGeneratorController(TestGenerationService testService) {
        this.testService = testService;
    }

    @PostMapping
    public ResponseEntity<String> generateTests(@RequestBody String javaClassSource) {
        String generatedTests = testService.generateTests(javaClassSource);
        return ResponseEntity.ok(generatedTests);
    }
}
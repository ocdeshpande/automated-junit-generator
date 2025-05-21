package com.netcracker.poc.service;

import com.netcracker.poc.component.GenAIClient;
import org.springframework.stereotype.Service;

@Service
public class TestGenerationService {

    private final GenAIClient genAIClient;

    public TestGenerationService(GenAIClient genAIClient) {
        this.genAIClient = genAIClient;
    }

    public String generateTests(String javaClassSource) {
        String prompt = """
            Generate comprehensive JUnit 5 test methods for the following Java class:
            ```
            %s
            ```
            Please respond only with Java code.
        """.formatted(javaClassSource);

        return genAIClient.callModel(prompt);
    }
}

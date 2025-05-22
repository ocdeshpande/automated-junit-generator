package com.netcracker.poc.service;

import com.netcracker.poc.component.TogetherAIApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JUnitGenerationService {

    @Autowired
    TogetherAIApiClient client;

    public String generateTests(String javaClassSource) {
        String prompt = "Generate a JUnit 5 test case for the following Java method:\n\n"+javaClassSource;
        String content = client.call(prompt);
        content = content.substring(content.indexOf("```java")+7,content.lastIndexOf("```"));
        System.out.println(content);
        return content;
    }
}

package com.netcracker.poc.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TogetherAIApiClient {
    @Value("${together.ai.api.key}")
    private String apiKey;

    @Value("${together.ai.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String call(String prompt) {
        prompt = prompt.replace("\r\n", "\n");

        HttpEntity<Map<String, Object>> request = createRequest(prompt);
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(apiUrl, request, Map.class);
        List<Map<String, Object>> choices = (List<Map<String, Object>>) responseEntity.getBody().get("choices");
        return (String) ((Map<String, Object>) choices.get(0).get("message")).get("content");

    }
    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        return headers;
    }

    private HttpEntity<Map<String, Object>> createRequest(String prompt){
        HttpHeaders headers = getHeaders();
        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);
        Map<String, Object> body = Map.of(
                "model", "meta-llama/Llama-3.3-70B-Instruct-Turbo-Free",
                "messages", new Object[]{message},
                "temperature", 0.7
        );
        return new HttpEntity<>(body, headers);
    }
}

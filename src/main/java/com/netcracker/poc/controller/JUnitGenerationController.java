package com.netcracker.poc.controller;

import com.netcracker.poc.service.JUnitGenerationService;
import com.netcracker.poc.validater.UploadFileValidater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/file")
public class JUnitGenerationController {

    @Autowired
    JUnitGenerationService service;

    @PostMapping("/upload")
    public ResponseEntity<byte[]> javaFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);
        if (!UploadFileValidater.isValidJavaFile(file.getOriginalFilename())){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid input file".getBytes());
        }
        String testCases = service.generateTests(content);
        byte[] outputBytes = testCases.getBytes(StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"processed_" + file.getOriginalFilename() + "\"")
                .contentType(MediaType.TEXT_PLAIN)
                .body(outputBytes);
    }
}

package com.campus.management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> checkHealth() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "AI-Powered Campus Management System");
        response.put("version", "1.0.0-SNAPSHOT");
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }
}

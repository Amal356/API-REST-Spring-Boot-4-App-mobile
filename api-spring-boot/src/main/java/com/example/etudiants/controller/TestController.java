package com.example.etudiants.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    public static class TestResponse {
        private String status;
        private String message;
        private long timestamp;

        public TestResponse(String status, String message, long timestamp) {
            this.status = status;
            this.message = message;
            this.timestamp = timestamp;
        }

        public String getStatus() { return status; }
        public String getMessage() { return message; }
        public long getTimestamp() { return timestamp; }
    }

    @GetMapping("/api/test")
    public TestResponse test() {
        return new TestResponse("OK", "API fonctionne", System.currentTimeMillis());
    }
}

package com.faq_chatbot_backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

@RestController
@RequestMapping("/chat-bot")
@CrossOrigin(origins = "*") // Allow frontend access
public class FaqChatbotController {

    @Value("${openrouter.api.key}")
    private String apiKey; // OpenRouter API Key

    private final String openRouterUrl = "https://openrouter.ai/api/v1/chat/completions";
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/chat/{prompt}")
    public ResponseEntity<String> getChatResponse(@PathVariable String prompt) {
        try {
            // Prepare request headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);
            headers.set("Content-Type", "application/json");
            headers.set("HTTP-Referer", "http://localhost:3000"); // Replace with your frontend URL
            headers.set("X-Title", "My Chatbot");

            // Prepare request body
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "deepseek/deepseek-r1:free");
            
            List<Map<String, String>> messages = new ArrayList<>();
            Map<String, String> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            userMessage.put("content", prompt);
            messages.add(userMessage);

            requestBody.put("messages", messages);

            // Convert request body to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = objectMapper.writeValueAsString(requestBody);

            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

            // Make API request
            ResponseEntity<String> response = restTemplate.exchange(
                    openRouterUrl, HttpMethod.POST, entity, String.class);

            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Something went wrong.\"}");
        }
    }
}

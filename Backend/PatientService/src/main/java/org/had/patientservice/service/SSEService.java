package org.had.patientservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.sse.SseEvent;
import org.had.accountservice.exception.MyWebClientException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@RestController
public class SSEService {
    private final Map<String, SseEmitter> emitters = new HashMap<>();
    public SseEmitter createSseEmitter(String requestId) {
        SseEmitter sseEmitter = new SseEmitter(10000L);
        emitters.put(requestId, sseEmitter);
        sseEmitter.onCompletion(() -> removeEmitter(requestId));
        sseEmitter.onTimeout(() -> removeEmitter(requestId));
        sseEmitter.onError((e) -> removeEmitter(requestId));
        return sseEmitter;
    }

    private void removeEmitter(String registrationId) {
        emitters.remove(registrationId);
    }

    public void sendUserAuthData(String jsonData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonData);
        String requestId = jsonNode.get("resp").get("requestId").asText();
        SseEmitter emitter = emitters.get(requestId);
        if (emitter != null) {
            try {
                emitter.send(jsonData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No SSE emitter found for requestId: " + requestId);
        }
    }


}
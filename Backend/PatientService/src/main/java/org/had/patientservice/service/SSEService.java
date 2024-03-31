package org.had.patientservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SSEService {
    private final Map<String, SseEmitter> emitters = new HashMap<>();
    public SseEmitter createSseEmitter(String requestId) {
        SseEmitter sseEmitter = new SseEmitter(5000L);
        emitters.put(requestId, sseEmitter);
        sseEmitter.onCompletion(() -> removeEmitter(requestId));
        sseEmitter.onTimeout(() -> removeEmitter(requestId));
        sseEmitter.onError((e) -> removeEmitter(requestId));
        return sseEmitter;
    }

    private void removeEmitter(String registrationId) {
        emitters.remove(registrationId);
    }

    public void sendErrorMessage(String message,int status,SseEmitter emitter){
        try {
            emitter.send(ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(message));
            emitter.complete();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void sendUserAuthInitData(JsonNode data) throws JsonProcessingException {
        String requestId = data.get("resp").get("requestId").asText();
        SseEmitter emitter = emitters.get(requestId);
        if (emitter != null) {
            if(data.hasNonNull("auth")){
                sendErrorMessage(data.get("error").get("message").asText(),HttpStatus.BAD_REQUEST.value(),emitter);
                System.err.println(data.get("error").get("message").asText());
            }
            else{
                try {
                    emitter.send(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(data.asText()));
                    emitter.complete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("No SSE emitter found for requestId: " + requestId);
        }
    }

    public void sendUserAuthVerifyData(JsonNode data) throws JsonProcessingException {
        String requestId = data.get("resp").get("requestId").asText();
        SseEmitter emitter = emitters.get(requestId);
        if (emitter != null) {
            if(data.hasNonNull("auth")){
                sendErrorMessage(data.get("error").get("message").asText(),HttpStatus.BAD_REQUEST.value(),emitter);
                System.err.println(data.get("error").get("message").asText());
            }
            else{
                try {
                    emitter.send(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(data.asText()));
                    emitter.complete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("No SSE emitter found for requestId: " + requestId);
        }
    }


}

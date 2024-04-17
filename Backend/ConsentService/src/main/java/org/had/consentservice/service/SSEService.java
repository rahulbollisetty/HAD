package org.had.consentservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.had.consentservice.entity.ConsentArtefact;
import org.had.consentservice.entity.ConsentRequest;
import org.had.consentservice.repository.ConsentArtefactRepository;
import org.had.consentservice.repository.ConsentRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class SSEService {

    @Autowired
    private ConsentRequestRepository consentRequestRepository;

    private final Map<String, SseEmitter> emitters = new HashMap<>();

    @Autowired
    private ConsentArtefactRepository consentArtefactRepository;

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

    public void sendErrorMessage(String message,int status,SseEmitter emitter){
        try {
            emitter.send(ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(message));
            emitter.complete();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void consentOnInit(JsonNode jsonNode) {
        System.out.println("Inside Consent On Init Service RabbitMQ");
        String requestId = jsonNode.get("response").get("requestId").asText();
        SseEmitter emitter = emitters.get(requestId);
        if (emitter != null) {
            if(jsonNode.hasNonNull("error")){
                sendErrorMessage(jsonNode.get("error").get("message").asText(), HttpStatus.BAD_REQUEST.value(),emitter);
                System.err.println(jsonNode.get("error").get("message").asText());
                ConsentRequest consentRequest = consentRequestRepository.findByRequest_id(requestId).get();
                consentRequestRepository.delete(consentRequest);
                System.out.println("Error Initializing Consent");
            }
            else {
                try {
                    ConsentRequest consentRequest = consentRequestRepository.findByRequest_id(requestId).get();
                    String consentRequestId = jsonNode.get("consentRequest").get("id").asText();
                    consentRequest.setConsent_id(consentRequestId);
                    consentRequestRepository.save(consentRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("No SSE emitter found for requestId: " + requestId);
        }
    }





}

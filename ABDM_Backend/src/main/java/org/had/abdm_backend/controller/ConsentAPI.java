package org.had.abdm_backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.had.abdm_backend.service.ABDMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/abdm/consent")
@CrossOrigin("http://localhost:5173")
public class ConsentAPI {
    @Autowired
    private ABDMService abdmService;

    @PostMapping(value = "/consentInit",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> consentInit(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        System.out.println("Consent Init Controller");
        abdmService.setToken();
        abdmService.consentInit(jsonNode);
        return ResponseEntity.ok("consent initiated");
    }

    @PostMapping(value = "/consentStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> consentStatus(@RequestBody JsonNode jsonNode) throws JsonProcessingException{
        abdmService.setToken();
        System.out.println("Consent Status Controller");
        abdmService.consentStatus(jsonNode);
        return ResponseEntity.ok("status request initiated");
    }

    @PostMapping(value = "/HIUOnNotify", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> HIUOnNotify(@RequestBody JsonNode jsonNode) throws JsonProcessingException{
        abdmService.setToken();
        System.out.println("Consent HIUNotify Controller");
        String requestId = jsonNode.get("requestId").asText();
        String consentRequestId = jsonNode.get("consentRequestId").asText();
        abdmService.HIUOnNotify(requestId, consentRequestId);
        return ResponseEntity.ok("consent initiated");
    }

    @PostMapping(value = "/consentFetch", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> consentFetch(@RequestBody JsonNode jsonNode) throws JsonProcessingException{
        abdmService.setToken();
        System.out.println("Consent fetch Controller");
        abdmService.consentFetch(jsonNode);
        return ResponseEntity.ok("consent initiated");
    }

    @PostMapping(value = "/hipOnNotify", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> hipOnNotify(@RequestBody JsonNode jsonNode) throws JsonProcessingException{
        abdmService.setToken();
        System.out.println("Consent HIP notify Controller");
        abdmService.hipOnNotify(jsonNode);
        return ResponseEntity.ok("notified");
    }

    @PostMapping(value = "/linkCareContext", produces = MediaType.APPLICATION_JSON_VALUE)
    public String linkCareContext(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        String opId = jsonNode.get("opId").asText();
        String accessToken = jsonNode.get("accessToken").asText();
        return abdmService.linkCareContext(opId, accessToken);
    }




}

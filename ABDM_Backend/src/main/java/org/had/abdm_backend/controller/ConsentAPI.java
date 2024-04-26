package org.had.abdm_backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.had.abdm_backend.service.ABDMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/abdm/consent")
@CrossOrigin("http://localhost:5173")
public class ConsentAPI {
    @Autowired
    private ABDMService abdmService;

    @PostMapping(value = "/consentInit",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> consentInit(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        log.info("Consent Init Controller");
        abdmService.setToken();
        abdmService.consentInit(jsonNode);
        return ResponseEntity.ok("consent initiated");
    }

    @PostMapping(value = "/consentStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> consentStatus(@RequestBody JsonNode jsonNode) throws JsonProcessingException{
        abdmService.setToken();
        log.info("Consent Status Controller");
        abdmService.consentStatus(jsonNode);
        return ResponseEntity.ok("status request initiated");
    }

    @PostMapping(value = "/HIUOnNotify", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> HIUOnNotify(@RequestBody JsonNode jsonNode) throws JsonProcessingException{
        abdmService.setToken();
        log.info("Consent HIUNotify Controller");
        String requestId = jsonNode.get("requestId").asText();
        String consentRequestId = jsonNode.get("consentRequestId").asText();
        abdmService.HIUOnNotify(requestId, consentRequestId);
        return ResponseEntity.ok("consent initiated");
    }

    @PostMapping(value = "/consentFetch", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> consentFetch(@RequestBody JsonNode jsonNode) throws JsonProcessingException{
        abdmService.setToken();
        log.info("Consent fetch Controller");
        abdmService.consentFetch(jsonNode);
        return ResponseEntity.ok("Consent Fetch initiated");
    }

//    @PostMapping(value = "/hipOnNotify", produces = MediaType.APPLICATION_JSON_VALUE)
//    ResponseEntity<?> hipOnNotify(@RequestBody JsonNode jsonNode) throws JsonProcessingException{
//        abdmService.setToken();
//        log.info("Consent HIP notify Controller");
//        abdmService.hipOnNotify(jsonNode);
//        return ResponseEntity.ok("notified");
//    }

    @PostMapping(value = "/linkCareContext", produces = MediaType.APPLICATION_JSON_VALUE)
    public String linkCareContext(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        String appointmentId = jsonNode.get("appointmentId").asText();
        String accessToken = jsonNode.get("accessToken").asText();
        String patientId = jsonNode.path("patientId").asText();
        String patientName = jsonNode.path("patientName").asText();
        String hospitalId = jsonNode.path("hospitalId").asText();
        String appointmentDate = jsonNode.get("date").asText();
        return abdmService.linkCareContext(appointmentId, accessToken,appointmentDate, patientId, patientName, hospitalId);
    }

//    @PostMapping(value = "/hipOnRequest", produces = MediaType.APPLICATION_JSON_VALUE)
//    ResponseEntity<?> hipOnRequest(@RequestBody JsonNode jsonNode) throws JsonProcessingException{
//        abdmService.setToken();
//        abdmService.hipOnRequest(jsonNode);
//        return ResponseEntity.ok("requested");
//    }

    @PostMapping(value = "/healthInfoNotify", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> healthInfoNotify(@RequestBody JsonNode jsonNode) throws JsonProcessingException{
        abdmService.setToken();
        String resp = abdmService.healthInfoNotify(jsonNode);
        return ResponseEntity.ok("healthInfoNotify response: "+resp);
    }

    @PostMapping(value = "healthInfoCmRequest",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> healthInfoCmRequest(@RequestBody JsonNode jsonNode) throws JsonProcessingException{
        abdmService.setToken();
        abdmService.healthInfoCmRequest(jsonNode);
        return ResponseEntity.ok("req");
    }

//    @PostMapping(value = "dataPush",produces = MediaType.APPLICATION_JSON_VALUE)
//    ResponseEntity<?> dataPush(@RequestBody JsonNode jsonNode) throws JsonProcessingException{
//        abdmService.setToken();
//        abdmService.dataPush(jsonNode);
//        return ResponseEntity.ok("data pushed");
//    }
//
}

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
        abdmService.setToken();
        abdmService.consentInit(jsonNode);
        return ResponseEntity.ok("consent initiated");
    }



}

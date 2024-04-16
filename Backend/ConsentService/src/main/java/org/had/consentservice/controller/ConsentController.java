package org.had.consentservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.had.consentservice.service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/consent/")
public class ConsentController {

    @Autowired
    private ConsentService consentService;

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping(value = "/consentInit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SseEmitter> consentInit(@RequestBody JsonNode jsonNode) {
        System.out.println("consentInit");
        return consentService.consentInit(jsonNode);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping(value = "/consentStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public String consentStatus(@RequestBody JsonNode jsonNode) {
        System.out.println("consentStatus");
        return consentService.consentStatus(jsonNode);
    }



}

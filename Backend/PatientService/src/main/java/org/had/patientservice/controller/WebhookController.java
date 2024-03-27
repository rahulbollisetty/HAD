package org.had.patientservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient/webhook")
@Slf4j
public class WebhookController {
    @PostMapping("/hello")
    public String hello(@RequestBody JsonNode jsonNode){
        System.out.println(jsonNode);
        return "hello";
    }
}

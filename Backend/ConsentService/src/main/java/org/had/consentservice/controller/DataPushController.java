package org.had.consentservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.had.consentservice.service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/data/push")
public class DataPushController {

    @Autowired
    private ConsentService consentService;

    @PostMapping
    public void dataPush(@RequestBody JsonNode jsonNode) throws Exception {
        consentService.dataPushHandler(jsonNode);
    }
}
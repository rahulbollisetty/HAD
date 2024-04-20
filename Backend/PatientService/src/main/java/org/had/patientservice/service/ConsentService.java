package org.had.patientservice.service;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsentService {

    public void consentOnNotifyHIP(JsonNode jsonNode){
        log.info("consentOnNotifyHIP: " + jsonNode.toString());
    }
}

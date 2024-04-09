package org.had.abdm_backend.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.had.abdm_backend.service.ABDMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/abdm/consent")
@RestController
public class ConsentApi {

    @Autowired
    ABDMService abdmService;

    @PostMapping(value = "/linkCareContext", produces = MediaType.APPLICATION_JSON_VALUE)
    public String linkCareContext(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        String opId = jsonNode.get("opId").asText();
        String accessToken = jsonNode.get("accessToken").asText();
        return abdmService.linkCareContext(opId, accessToken);
    }
}

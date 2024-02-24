package org.had.abdm_backend.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.had.abdm_backend.service.ABDMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hpr")
public class HprApi {

    @Autowired
    private ABDMService abdmService;

    @PostMapping(value = "/getdoctordetails",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDoctorDetails(@RequestBody JsonNode doctorHPR) throws JsonProcessingException {
        abdmService.setToken();
        String details = abdmService.getDoctorDetails(doctorHPR.get("hprId").asText(), doctorHPR.get("password").asText());
        return  ResponseEntity.ok(details);
    }
}

package org.had.abdm_backend.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.had.abdm_backend.service.ABDMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/abdm/hpr")
@CrossOrigin("http://localhost:5173 ")
public class HprApi {

    @Autowired
    private ABDMService abdmService;
    @PostMapping(value = "/getdoctordetails",produces = MediaType.APPLICATION_JSON_VALUE)
    public String getDoctorDetails(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        String otp = jsonNode.get("otp").asText();
        String txnId = jsonNode.get("txnId").asText();
        return abdmService.getDoctorDetails(otp, txnId);
    }

    @PostMapping(value = "/getLgdStatesList",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStatesList() throws JsonProcessingException {
        abdmService.setToken();
        String details = abdmService.getLgdStatesList();
        return  ResponseEntity.ok(details);
    }

    @PostMapping(value = "/registerFacility", produces = MediaType.APPLICATION_JSON_VALUE)
    public String registerFacility(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        System.out.println("Helloo");
        return abdmService.registerFacility(jsonNode);
    }

    @PostMapping(value = "/generateAadharOTPHPR", produces = MediaType.APPLICATION_JSON_VALUE)
    public String generateAadharOTPHPR(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        String hprId = jsonNode.get("hprId").asText();
        return abdmService.generateAadharOTPHPR(hprId);
    }
}

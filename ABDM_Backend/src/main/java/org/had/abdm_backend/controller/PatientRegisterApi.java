package org.had.abdm_backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import org.had.abdm_backend.service.ABDMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/abdm/patient")
@CrossOrigin("http://localhost:5173")
public class PatientRegisterApi {
    @Autowired
    private ABDMService abdmService;

    @PostMapping(value = "/aadhaarOTPInit",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> aadhaarOTPInit(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        String aadhar = jsonNode.get("aadhaar").asText();
        String details = abdmService.aadharOtpInit(aadhar);
        return ResponseEntity.ok(details);
    }

    @PostMapping(value = "/aadhaarOTPVerify",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> aadhaarOTPVerify(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        String otp = jsonNode.get("otp").asText();
        String txnId = jsonNode.get("transactionId").asText();
        String details = abdmService.aadharOtpVerify(otp, txnId);
        return ResponseEntity.ok(details);
    }

    @PostMapping(value = "/checkAndMobileOTPInit",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkAndMobileOTPInit(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        String txnId = jsonNode.get("txnId").asText();
        String mobile = jsonNode.get("mobile").asText();
        String details = abdmService.mobileOtpInit(txnId, mobile);
        return ResponseEntity.ok(details);
    }

    @PostMapping(value = "/mobileOTPVerify",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> mobileOTPVerify(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        String txnId = jsonNode.get("txnId").asText();
        String otp = jsonNode.get("otp").asText();
        String details = abdmService.mobileOTPVerify(txnId, otp);
        return ResponseEntity.ok(details);
    }
    @PostMapping(value = "/createHealthId",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createHealthId(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        String txnId = jsonNode.get("txnId").asText();
        String details = abdmService.createHealthId(txnId);
        return ResponseEntity.ok(details);
    }


    @PostMapping(value = "/getProfileDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getProfileDetails(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
            abdmService.setToken();
            String authToken = jsonNode.get("authToken").asText();
            return abdmService.getProfileDetails(authToken);
    }

    @PostMapping(value = "/healthIdSuggestions", produces = MediaType.APPLICATION_JSON_VALUE)
    public String healthIdSuggestions(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        String transactionId = jsonNode.get("transactionId").asText();
        return abdmService.healthIdSuggestions(transactionId);
    }

    @PostMapping(value = "/checkPHRAddressExist", produces = MediaType.APPLICATION_JSON_VALUE)
    public String checkPHRAddressExist(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        String Id = jsonNode.get("Id").asText();
        return abdmService.checkPHRAddressExist(Id);
    }

    @PostMapping(value = "/createPHRAddress", produces = MediaType.APPLICATION_JSON_VALUE)
    public String createPHRAddress(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        String phrAddress = jsonNode.get("phrAddress").asText();
        String transactionId = jsonNode.get("transactionId").asText();
        return abdmService.createPHRAddress(phrAddress, transactionId);
    }



    @PostMapping(value = "/userAuthInit",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> userAuthInit(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        String patientSBXId = jsonNode.get("patientSBXId").asText();
        String requesterId = jsonNode.get("requesterId").asText();
        String requesterType = jsonNode.get("requesterType").asText();
        String remoteAddr = jsonNode.get("routingKey").asText();
        String requestId = jsonNode.get("requestId").asText();
        abdmService.userAuthInit(patientSBXId, requesterId, requesterType,remoteAddr,requestId);
        return ResponseEntity.ok("OTP Sent");
    }

<<<<<<< HEAD

    @PostMapping(value = "/userAuthOtpVerify",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> userAuthOtpVerify(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        String transactionId = jsonNode.get("transactionId").asText();
        String authCode = jsonNode.get("otp").asText();
        String details = abdmService.userAuthOTPVerify(transactionId, authCode);
        return ResponseEntity.ok("OTP Verified");
=======
    @PostMapping(value = "/userAuthVerify",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> userAuthVerify(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        String transactionId = jsonNode.get("transactionId").asText();
        String name = jsonNode.get("name").asText();
        String gender = jsonNode.get("gender").asText();
        String dob = jsonNode.get("dob").asText();
        String requestId = jsonNode.get("requestId").asText();
        String details = abdmService.userAuthOTPVerify(transactionId, name, gender, dob, requestId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Details Sent for Verification");
>>>>>>> 7d2f060 (patient Auth added)
    }






}

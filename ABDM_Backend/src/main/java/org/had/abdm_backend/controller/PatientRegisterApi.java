package org.had.abdm_backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import org.had.abdm_backend.service.ABDMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

    @PostMapping(value = "/aadhaarOTPinit",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> aadhaarOTPinit(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        String aadhar = jsonNode.get("aadhaar").asText();
        String details = abdmService.aadharOtpInit(aadhar);
        return ResponseEntity.ok(details);
    }

    @PostMapping(value = "/aadhaarOTPverify",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> aadhaarOTPverify(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        String otp = jsonNode.get("otp").asText();
        String txnId = jsonNode.get("transactionId").asText();
        String mobile = jsonNode.get("mobileNumber").asText();
        String details = abdmService.aadharOtpVerify(otp,mobile,txnId);
        return ResponseEntity.ok(details);
    }

    @PostMapping(value = "/mobileOTPinit",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> mobileOTPinit(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        String txnId = jsonNode.get("txnId").asText();
        String loginId = jsonNode.get("loginId").asText();
        String details = abdmService.mobileOtpInit(txnId,loginId);
        return ResponseEntity.ok(details);
    }

    @PostMapping(value = "/mobileOTPverify",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> mobileOTPverify(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        String txnId = jsonNode.get("txnId").asText();
        String otp = jsonNode.get("otp").asText();
        String details = abdmService.mobileOtpVerify(otp,txnId);
        return ResponseEntity.ok(details);
    }

    @PostMapping(value = "/userAuthInit",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> userAuthInit(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        String patientSBXId = jsonNode.get("patientSBXId").asText();
        String requesterId = jsonNode.get("requesterId").asText();
        String requesterType = jsonNode.get("requesterType").asText();
        String remoteAddr = jsonNode.get("routingKey").asText();
<<<<<<< HEAD
<<<<<<< HEAD
        String requestId = jsonNode.get("requestId").asText();
        String details = abdmService.userAuthInit(patientSBXId, requesterId, requesterType,remoteAddr,requestId);
=======
        String details = abdmService.userAuthInit(patientSBXId, requesterId, requesterType,remoteAddr);
>>>>>>> 758bc15 (webhook added)
=======
        String requestId = jsonNode.get("requestId").asText();
        String details = abdmService.userAuthInit(patientSBXId, requesterId, requesterType,remoteAddr,requestId);
>>>>>>> 3229706 (sse added and connected with rabbitmq)
        return ResponseEntity.ok("OTP Sent");
    }

    @PostMapping(value = "/userAuthOtpVerify",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> userAuthOtpVerify(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        abdmService.setToken();
        String transactionId = jsonNode.get("transactionId").asText();
        String authCode = jsonNode.get("authCode").asText();
        String details = abdmService.userAuthOTPVerify(transactionId, authCode);
        return ResponseEntity.ok("OTP Verified");
    }




}

package org.had.patientservice.controller;


import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.had.patientservice.service.PatientService;
import org.had.patientservice.service.RabbitMqService;
import org.had.patientservice.service.SSEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;


@RestController
@RequestMapping("/patient")
@Slf4j
public class PatientController {

    @Autowired
    private PatientService patientService;



    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping("/hello")
    public String hello(){
        return "hello";
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping(value = "/aadhaarOTPInit",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> requestAadharOTP(@RequestBody JsonNode jsonNode){
        String aadhaar = jsonNode.get("aadhaar").asText();
        String result = patientService.requestAadharOtp(aadhaar);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping(value = "/aadharOTPVerify",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> verifyAadharOTP(@RequestBody JsonNode jsonNode) {
        String otp = jsonNode.get("otp").asText();
        String transactionId = jsonNode.get("transactionId").asText();
        String result = patientService.verifyAadharOTP(otp, transactionId);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping(value = "/checkAndMobileOTPInit",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkAndMobileOTPinit(@RequestBody JsonNode jsonNode) {
        String mobile = jsonNode.get("mobile").asText();
        String txnId = jsonNode.get("txnId").asText();
        String result = patientService.checkAndMobileOTPinit(mobile, txnId);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping(value = "/mobileOTPVerify",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> mobileOTPVerify(@RequestBody JsonNode jsonNode) {
        String otp = jsonNode.get("otp").asText();
        String txnId = jsonNode.get("txnId").asText();
        String result = patientService.mobileOTPVerify(otp, txnId);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping(value = "/createHealthId",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createHealthId(@RequestBody JsonNode jsonNode) {
        String txnId = jsonNode.get("txnId").asText();
        String result = patientService.createHealthId(txnId);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping(value = "/getProfileDetails",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProfileDetails(@RequestBody JsonNode jsonNode) {
        String authToken = jsonNode.get("authToken").asText();
        String result = patientService.getProfileDetails(authToken);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping(value = "/checkPHRAddressExist",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkPHRAddressExist(@RequestBody JsonNode jsonNode) {
        String Id = jsonNode.get("Id").asText();
        String result = patientService.checkPHRAddressExist(Id);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping(value = "/createPHRAddress",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPHRAddress(@RequestBody JsonNode jsonNode) {
        String phrAddress = jsonNode.get("phrAddress").asText();
        String transactionId = jsonNode.get("transactionId").asText();
        String result = patientService.createPHRAddress(phrAddress, transactionId);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping(value = "/auth/userAuthInit",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> userAuthInit(@RequestBody JsonNode jsonNode, HttpServletRequest request) throws IOException {
        String patientSBXId = jsonNode.get("patientSBXId").asText();
        String requesterId = jsonNode.get("requesterId").asText();
        String requesterType = jsonNode.get("requesterType").asText();
        return patientService.userAuthInit(patientSBXId, requesterId, requesterType);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping(value = "/userOTPVerify")
    public String userOTPVerify(@RequestBody JsonNode jsonNode, HttpServletRequest request) {
        String transactionId = jsonNode.get("transactionId").asText();
        String OTP = jsonNode.get("OTP").asText();
        return patientService.userOTPVerify(transactionId, OTP);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping(value = "/savePatient")
    public String savePatient(@RequestBody JsonNode jsonNode, HttpServletRequest request) {
        String fullName = jsonNode.get("fullName").asText();
        String abhaAddress = jsonNode.get("abhaAddress").asText();
        String address = jsonNode.get("address").asText();
        String yearOfBirth = jsonNode.get("yearOfBirth").asText();
        String mobileNumber = jsonNode.get("mobileNumber").asText();
        String gender = jsonNode.get("gender").asText();

        return patientService.savePatient(fullName, abhaAddress, address, yearOfBirth, mobileNumber, gender);
    @PostMapping(value = "/auth/userAuthVerify",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> userAuthVerify(@RequestBody JsonNode jsonNode, HttpServletRequest request) throws IOException {
        String txnId = jsonNode.get("transactionId").asText();
        String name = jsonNode.get("name").asText();
        String gender = jsonNode.get("requesterType").asText();
        String dob = jsonNode.get("dob").asText();
        return patientService.userAuthVerify(txnId, name, gender, dob);
    }

}

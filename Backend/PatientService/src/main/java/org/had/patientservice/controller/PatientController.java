package org.had.patientservice.controller;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.had.patientservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping(value = "/aadhaarOTPinit",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> requestAadharOTP(@RequestBody JsonNode jsonNode){
        String aadhaar = jsonNode.get("aadhaar").asText();
        String result = patientService.requestAadharOtp(aadhaar);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping(value = "/aadharOTPverify",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> verifyAadharOTP(@RequestBody JsonNode jsonNode) {
        String otp = jsonNode.get("otp").asText();
        String mobileNumber = jsonNode.get("mobileNumber").asText();
        String transactionId = jsonNode.get("transactionId").asText();
        String result = patientService.verifyAadharOTP(otp, mobileNumber, transactionId);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping(value = "/mobileOTPinit",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> requestMobileOTP(@RequestBody JsonNode jsonNode) {
        String loginId = jsonNode.get("loginId").asText();
        String txnId = jsonNode.get("txnId").asText();
        String result = patientService.requestMobileOTP(loginId, txnId);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping(value = "/mobileOTPverify",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> verifyMobileOTP(@RequestBody JsonNode jsonNode) {
        String otp = jsonNode.get("otp").asText();
        String txnId = jsonNode.get("txnId").asText();
        String result = patientService.verifyMobileOTP(otp, txnId);
        return ResponseEntity.ok(result);
    }

}

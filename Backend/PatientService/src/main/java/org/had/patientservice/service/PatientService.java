package org.had.patientservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.had.accountservice.exception.MyWebClientException;
import org.had.patientservice.dto.PatientDetailsDto;
import org.had.patientservice.entity.PatientDetails;
import org.had.patientservice.repository.PatientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@Service
public class PatientService {

    @Autowired
    private WebClient webClient;

    @Value("${routingKey.name}")
    private String routingKey;

    @Autowired
    private SSEService sseService;

    @Autowired
    private PatientDetailsRepository patientDetailsRepository;

    public String requestAadharOtp(String aadhar) {
        var values = new HashMap<String, String>() {{
            put("aadhaar", aadhar);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri("http://127.0.0.1:9008/abdm/patient/aadhaarOTPInit")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public String verifyAadharOTP(String otp, String transactionId) {
        var values = new HashMap<String, String>() {{
            put("otp", otp);
            put("transactionId",transactionId);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri("http://127.0.0.1:9008/abdm/patient/aadhaarOTPVerify")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();

    }

    public String checkAndMobileOTPinit(String mobile, String txnId) {
        var values = new HashMap<String, String>() {{
            put("mobile", mobile);
            put("txnId",txnId);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri("http://127.0.0.1:9008/abdm/patient/checkAndMobileOTPInit")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public String mobileOTPVerify(String otp, String txnId) {
        var values = new HashMap<String, String>() {{
            put("otp", otp);
            put("txnId",txnId);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri("http://127.0.0.1:9008/abdm/patient/mobileOTPVerify")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public String createHealthId(String txnId) {
        var values = new HashMap<String, String>() {{
            put("txnId",txnId);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri("http://127.0.0.1:9008/abdm/patient/createHealthId")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public String getProfileDetails(String authToken) {
        var values = new HashMap<String, String>() {{
            put("authToken",authToken);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri("http://127.0.0.1:9008/abdm/patient/getProfileDetails")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public String healthIdSuggestions(String txnId) {
        var values = new HashMap<String, String>() {{
            put("transactionId",txnId);
        }};
        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri("http://127.0.0.1:9008/abdm/patient/healthIdSuggestions")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public String checkPHRAddressExist(String Id) {
        var values = new HashMap<String, String>() {{
            put("Id",Id);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri("http://127.0.0.1:9008/abdm/patient/checkPHRAddressExist")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public String createPHRAddress(String phrAddress, String transactionId) {
        var values = new HashMap<String, String>() {{
            put("phrAddress",phrAddress);
            put("transactionId", transactionId);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri("http://127.0.0.1:9008/abdm/patient/createPHRAddress")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();

    }


    public ResponseEntity<SseEmitter> userAuthInit(String patientSBXId, String requesterId, String requesterType) throws IOException {
        String requestId = UUID.randomUUID().toString();
        var values = new HashMap<String, String>() {{
            put("patientSBXId", patientSBXId);
            put("requesterId",requesterId);
            put("requesterType",requesterType);
            put("requestId",requestId);
            put("routingKey",routingKey);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        SseEmitter sseEmitter = sseService.createSseEmitter(requestId);
        try{
            String response = webClient.post().uri("http://127.0.0.1:9008/abdm/patient/userAuthInit")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
        return ResponseEntity.ok().body(sseEmitter);
        }
        catch (MyWebClientException e){
            sseService.sendErrorMessage(e.getMessage(),e.getStatus(),sseEmitter);
            return ResponseEntity.badRequest().body(sseEmitter);
        }

    }

    public ResponseEntity<SseEmitter> userAuthVerify(String txnId, String name, String gender, String dob) throws IOException {
        String requestId = UUID.randomUUID().toString();
        var values = new HashMap<String, String>() {{
            put("transactionId", txnId);
            put("name",name);
            put("gender",gender);
            put("dob",dob);
            put("requestId",requestId);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        SseEmitter sseEmitter = sseService.createSseEmitter(requestId);
        try{
            String response = webClient.post().uri("http://127.0.0.1:9008/abdm/patient/userAuthVerify")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(requestBody))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, clientResponse -> {
                        return clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                    })
                    .bodyToMono(String.class).block();
            return ResponseEntity.ok().body(sseEmitter);
        }
        catch (MyWebClientException e){
            sseService.sendErrorMessage(e.getMessage(),e.getStatus(),sseEmitter);
            return ResponseEntity.badRequest().body(sseEmitter);
        }

    }

    public ResponseEntity<?> savePatient(PatientDetailsDto patientDetailsDto) {
        if (patientDetailsRepository.existsByAbhaAddress(patientDetailsDto.getAbhaAddress())) {
            return ResponseEntity.badRequest().body("Abha Address already exists, Patient already exists");
        }

        PatientDetails patient = new PatientDetails();
        patient.setName(patientDetailsDto.getName());
        patient.setAbhaAddress(patientDetailsDto.getAbhaAddress());
        patient.setAbhaNumber(patientDetailsDto.getAbhaNumber());
        patient.setAddress(patientDetailsDto.getAddress());
        patient.setMobileNumber(patientDetailsDto.getMobileNumber());
        patient.setGender(patientDetailsDto.getGender());
        patient.setDob(patientDetailsDto.getDob());
        patient.setEmail(patientDetailsDto.getEmail());
        patient.setBloodGroup(patientDetailsDto.getBloodGroup());
        patient.setOccupation(patientDetailsDto.getOccupation());
        patient.setFamilyMemberName(patientDetailsDto.getFamilyMemberName());
        patient.setRelationship(patientDetailsDto.getRelationship());
        patient.setTown(patientDetailsDto.getTown());
        patient.setPincode(patientDetailsDto.getPincode());
        patient.setState(patientDetailsDto.getState());
        patientDetailsRepository.save(patient);
        return ResponseEntity.ok().body("Patient Saved");

    }


    public String getLgdStatesList() {
        return webClient.post().uri("http://127.0.0.1:9008/abdm/hpr/getLgdStatesList")
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

}


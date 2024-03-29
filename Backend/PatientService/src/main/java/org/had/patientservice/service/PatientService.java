package org.had.patientservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.had.accountservice.exception.MyWebClientException;
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
        return webClient.post().uri("http://127.0.0.1:9008/abdm/patient/aadhaarOTPinit")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public String verifyAadharOTP(String otp, String mobileNumber, String transactionId) {
        var values = new HashMap<String, String>() {{
            put("otp", otp);
            put("mobileNumber", mobileNumber);
            put("transactionId",transactionId);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri("http://127.0.0.1:9008/abdm/patient/aadhaarOTPverify")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();


    }

    public String requestMobileOTP(String loginId, String txnId) {
        var values = new HashMap<String, String>() {{
            put("loginId", loginId);
            put("txnId",txnId);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri("http://127.0.0.1:9008/abdm/patient/mobileOTPinit")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public String verifyMobileOTP(String otp, String txnId) {
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
        return webClient.post().uri("http://127.0.0.1:9008/abdm/patient/mobileOTPverify")
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

    public String userOTPVerify(String transactionId, String otp) {
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
        return webClient.post().uri("http://127.0.0.1:9008/abdm/patient/userAuthOtpVerify")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }
}


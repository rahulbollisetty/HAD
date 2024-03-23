package org.had.abdm_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.had.abdm_backend.exception.MyWebClientException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ABDMService {
    @Autowired
    private WebClient webClient;

    @Getter
    public String token;

    public String getDoctorDetails(String hprid, String password) throws JsonProcessingException {

        var values = new HashMap<String, String>() {{
            put("idType", "hpr_id");
            put ("domainName", "@hpr.abdm");
            put("hprId",hprid);
            put("password",password);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);

        String responseBody = webClient.post().uri("https://hpridsbx.abdm.gov.in/api/v1/auth/authPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+token)
                .body(BodyInserters.fromValue(requestBody.toString()))
                .retrieve()
                .onStatus(HttpStatusCode::isError,clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();//
        JSONObject jsonObject = new JSONObject(responseBody);
        String xtoken = jsonObject.get("token").toString();

        return webClient.get().uri("https://hpridsbx.abdm.gov.in/api/v1/account/profile")
                .header("Authorization","Bearer "+token)
                .header("X-Token","Bearer "+xtoken)
                .retrieve()
                .onStatus(HttpStatusCode::isError,clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }
    public void setToken() throws JsonProcessingException {
        var values = new HashMap<String, String>() {{
            put("clientId", "SBX_004922");
            put ("clientSecret", "ade1d968-7596-4826-8a9c-20408aa06962");
            put("grantType","client_credentials");
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);
         String responseBody = webClient.post().uri("https://dev.abdm.gov.in/gateway/v0.5/sessions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(requestBody.toString()))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError,clientResponse -> {
                        return clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                    })
                    .bodyToMono(String.class)
                    .block();
            JSONObject jsonObject = new JSONObject(responseBody);
            this.token = jsonObject.get("accessToken").toString();

    }

    public String aadharOtpInit(String aadhar) throws JsonProcessingException{


        var values = new HashMap<String,Object>(){{
            put("scope", Collections.singletonList("abha-enrol"));
            put("loginHint", "aadhaar");
            put("loginId",aadhar);
            put("otpSystem", "aadhaar");
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);

        return webClient.post().uri("https://abhasbx.abdm.gov.in/abha/api/v3/enrollment/request/otp")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+token)
                .header("REQUEST-ID",UUID.randomUUID().toString())
                .header("TIMESTAMP", getISOTimestamp())
                .body(BodyInserters.fromValue(requestBody.toString()))
                .retrieve()
                .onStatus(HttpStatusCode::isError,clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();//
    }

    public String getISOTimestamp(){
        Instant now = Instant.now();
        return now.toString();
    }
    public String getCurrentSimpleTimestamp(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
    public String aadharOtpVerify(String otp, String mobile, String txnId) throws JsonProcessingException{
        String timeStamp = getCurrentSimpleTimestamp();

        Map<String, Object> root = new HashMap<>();

        Map<String, Object> authData = new HashMap<>();

        List<String> authMethods = Arrays.asList("otp");
        authData.put("authMethods", authMethods);

        Map<String, String> otpDetails = new HashMap<>();
        otpDetails.put("timeStamp", timeStamp);
        otpDetails.put("txnId", txnId);
        otpDetails.put("otpValue", otp);
        otpDetails.put("mobile", mobile);
        authData.put("otp", otpDetails);

        root.put("authData", authData);

        Map<String, String> consent = new HashMap<>();
        consent.put("code", "abha-enrollment");
        consent.put("version", "1.4");

        root.put("consent", consent);

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(root);

        return webClient.post().uri("https://abhasbx.abdm.gov.in/abha/api/v3/enrollment/enrol/byAadhaar")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+token)
                .header("REQUEST-ID",UUID.randomUUID().toString())
                .header("TIMESTAMP", getISOTimestamp())
                .body(BodyInserters.fromValue(requestBody.toString()))
                .retrieve()
                .onStatus(HttpStatusCode::isError,clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();//
    }

    public String mobileOtpInit(String txnId, String loginId) throws JsonProcessingException{
        var values = new HashMap<String,Object>(){{
            put("txnId", txnId);
            put("scope", Arrays.asList("abha-enrol", "mobile-verify"));
            put("loginHint", "mobile");
            put("loginId",loginId);
            put("otpSystem", "abdm");
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);

        return webClient.post().uri("https://abhasbx.abdm.gov.in/abha/api/v3/enrollment/request/otp")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+token)
                .header("REQUEST-ID",UUID.randomUUID().toString())
                .header("TIMESTAMP", getISOTimestamp())
                .body(BodyInserters.fromValue(requestBody.toString()))
                .retrieve()
                .onStatus(HttpStatusCode::isError,clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();//
    }

    public String mobileOtpVerify(String otp, String txnId) throws JsonProcessingException{
        String timeStamp = getCurrentSimpleTimestamp();

        Map<String, Object> root = new HashMap<>();

        Map<String, String> otpMap = new HashMap<>();
        otpMap.put("timeStamp", timeStamp);
        otpMap.put("txnId", txnId);
        otpMap.put("otpValue", otp);

        List<String> authMethods = Arrays.asList("otp");

        Map<String, Object> authData = new HashMap<>();
        authData.put("authMethods", authMethods);
        authData.put("otp", otpMap);

        List<String> scope = Arrays.asList("abha-enrol", "mobile-verify");

        root.put("scope", scope);
        root.put("authData", authData);

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(root);

        return webClient.post().uri("https://abhasbx.abdm.gov.in/abha/api/v3/enrollment/auth/byAbdm")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+token)
                .header("REQUEST-ID",UUID.randomUUID().toString())
                .header("TIMESTAMP", getISOTimestamp())
                .body(BodyInserters.fromValue(requestBody.toString()))
                .retrieve()
                .onStatus(HttpStatusCode::isError,clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public String userAuthInit(String patientSBXId, String requesterId, String requesterType) throws JsonProcessingException {
        String timeStamp = getCurrentSimpleTimestamp();

        Map<String, Object> content = new HashMap<>();

        Map<String, String> requester = new HashMap<>();
        requester.put("type", requesterType);
        requester.put("id", requesterId);

        Map<String, Object> query = new HashMap<>();
        query.put("id", patientSBXId);
        query.put("purpose", "KYC_AND_LINK");
        query.put("authMode", "MOBILE_OTP");
        query.put("requester", requester);

        content.put("requestId", UUID.randomUUID().toString());
        content.put("timestamp", timeStamp);
        content.put("query", query);

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(content);

        return webClient.post().uri("https://dev.abdm.gov.in/gateway/v0.5/users/auth/init")
                .header("Authorization","Bearer "+token)
                .header("accept", "*/*")
                .header("X-CM-ID", "sbx")
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .header("Accept", MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(requestBody.toString()))
                .retrieve()
                .onStatus(HttpStatusCode::isError,clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }



//    private String handleErrorResponse(ClientResponse response) {
//        // Handle the error response
//        HttpStatus status = (HttpStatus) response.statusCode();
//        String responseBody = response.bodyToMono(String.class).block();
//
//        // Create a custom exception with error details
//        MyWebClientException exception = new MyWebClientException("Error during WebClient request");
//        exception.setStatus(status);
//        exception.setResponseBody(responseBody);
//        System.out.println(exception);
//        // Return the custom exception
//        return responseBody;
//    }
//
//    private Mono<String> handleOtherErrors(Throwable throwable) {
//        // Handle other types of errors
//        // You can log the error, return a default value, or perform other actions
//        return Mono.just("Default value in case of error");
//    }

}

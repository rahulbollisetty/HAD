package org.had.abdm_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.had.abdm_backend.entity.AbdmIdVerify;
import org.had.abdm_backend.exception.MyWebClientException;
import org.had.abdm_backend.repository.AbdmIdVerifyRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ABDMService {
    @Autowired
    private WebClient webClient;

    @Getter
    public String token;

    @Autowired
    private AbdmIdVerifyRepository abdmIdVerifyRepository;

    @Autowired
    private RabbitMqService rabbitMqService;

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
            put("aadhaar",aadhar);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);

        return webClient.post().uri("https://healthidsbx.abdm.gov.in/api/v2/registration/aadhaar/generateOtp")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+token)
                .header("accept", "*/*")
                .header("Accept-Language", "en-US")
                .body(BodyInserters.fromValue(requestBody.toString()))
                .retrieve()
                .onStatus(HttpStatusCode::isError,clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
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
    public String aadharOtpVerify(String otp, String txnId) throws JsonProcessingException{
        var values = new HashMap<String,Object>(){{
            put("otp",otp);
            put("txnId",txnId);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);

        return webClient.post().uri("https://healthidsbx.abdm.gov.in/api/v2/registration/aadhaar/verifyOTP")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+token)
                .header("accept", "*/*")
                .header("Accept-Language", "en-US")
                .body(BodyInserters.fromValue(requestBody.toString()))
                .retrieve()
                .onStatus(HttpStatusCode::isError,clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();//
    }

    public String mobileOtpInit(String txnId, String mobile) throws JsonProcessingException{
        var values = new HashMap<String,Object>(){{
            put("txnId", txnId);
            put("mobile", mobile);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);

        return webClient.post().uri("https://healthidsbx.abdm.gov.in/api/v2/registration/aadhaar/checkAndGenerateMobileOTP")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+token)
                .header("accept", "*/*")
                .header("Accept-Language", "en-US")
                .body(BodyInserters.fromValue(requestBody.toString()))
                .retrieve()
                .onStatus(HttpStatusCode::isError,clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public String mobileOTPVerify(String txnId, String otp) {
        var values = new HashMap<String,Object>(){{
            put("txnId", txnId);
            put("otp", otp);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = null;
        try {
            requestBody = objectMapper
                    .writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return webClient.post().uri("https://healthidsbx.abdm.gov.in/api/v2/registration/aadhaar/verifyMobileOTP")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+token)
                .header("accept", "*/*")
                .header("Accept-Language", "en-US")
                .body(BodyInserters.fromValue(requestBody.toString()))
                .retrieve()
                .onStatus(HttpStatusCode::isError,clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();

    }

    public String createHealthId(String txnId) throws JsonProcessingException{
        var values = new HashMap<String,Object>(){{
            put("txnId", txnId);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);

        return webClient.post().uri("https://healthidsbx.abdm.gov.in/api/v2/registration/aadhaar/createHealthIdByAdhaar")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+token)
                .header("accept", "*/*")
                .header("Accept-Language", "en-US")
                .body(BodyInserters.fromValue(requestBody.toString()))
                .retrieve()
                .onStatus(HttpStatusCode::isError,clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public String getProfileDetails(String authToken) {
        var values = new HashMap<String,Object>(){{
            put("authToken", authToken);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = null;
        try {
            requestBody = objectMapper
                    .writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri("https://phrsbx.abdm.gov.in/api/v1/phr/profile/link/profileDetails")
                .header("sec-ch-ua", "\"Not_A Brand;v=99\", \"Google Chrome;v=109\", \"Chromium;v=109\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("Authorization", "Bearer " + token)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36")
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("Referer", "https://phr.abdm.gov.in/register/health-id")
                .header("sec-ch-ua-platform", "Linux")
                .header("Sec-Fetch-Mode", "cors")
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError,clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class)
                .block();
    }

    public String healthIdSuggestions(String transactionId) {
        var values = new HashMap<String,Object>(){{
            put("transactionId", transactionId);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = null;
        try {
            requestBody = objectMapper
                    .writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return webClient.post().uri("https://phrsbx.abdm.gov.in/api/v1/phr/registration/phr/suggestion")
                .header("sec-ch-ua", "\"Not_A Brand;v=99\", \"Google Chrome;v=109\", \"Chromium;v=109\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("Authorization", "Bearer " + token)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36")
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("Referer", "https://phr.abdm.gov.in/register/health-id")
                .header("sec-ch-ua-platform", "Linux")
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError,clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class)
                .block();
    }

    public String checkPHRAddressExist(String Id) {

        return webClient.get().uri("https://phrsbx.abdm.gov.in/api/v1/phr/search/isExist?phrAddress=" + Id)
                .header("sec-ch-ua", "\"Not_A Brand;v=99\", \"Google Chrome;v=109\", \"Chromium;v=109\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("Authorization", "Bearer " + token)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36")
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("Referer", "https://phr.abdm.gov.in/register/health-id")
                .header("sec-ch-ua-platform", "Linux")
                .retrieve()
                .onStatus(HttpStatusCode::isError,clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class)
                .block();
    }

    public String createPHRAddress(String phrAddress, String transactionId) {
        var values = new HashMap<String,Object>(){{
            put("transactionId", transactionId);
            put("phrAddress", phrAddress);
            put("alreadyExistedPHR", "false");
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = null;
        try {
            requestBody = objectMapper
                    .writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return webClient.post().uri("https://phrsbx.abdm.gov.in/api/v1/phr/registration/hid/create-phr-address")
                .header("sec-ch-ua", "\"Not_A Brand;v=99\", \"Google Chrome;v=109\", \"Chromium;v=109\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("Authorization", "Bearer " + token)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36")
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("Referer", "https://phr.abdm.gov.in/register/health-id")
                .header("sec-ch-ua-platform", "Linux")
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError,clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class)
                .block();

    }



    public String userAuthInit(String patientSBXId, String requesterId, String requesterType, String routingKey, String requestId) throws JsonProcessingException {
        String timeStamp = getCurrentSimpleTimestamp();

        Map<String, Object> content = new HashMap<>();

        Map<String, String> requester = new HashMap<>();
        requester.put("type", requesterType);
        requester.put("id", patientSBXId);

        Map<String, Object> query = new HashMap<>();
        query.put("id", patientSBXId);
        query.put("purpose", "KYC_AND_LINK");
        query.put("authMode", "DEMOGRAPHICS");
        query.put("requester", requester);
        content.put("requestId", requestId);
        content.put("timestamp", timeStamp);
        content.put("query", query);

        // Storing in AbdmIdVerify entity
        AbdmIdVerify entity = new AbdmIdVerify();
        entity.setInitRequestId(requestId);
        entity.setRoutingKey(routingKey);
        abdmIdVerifyRepository.save(entity);

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

    public String userAuthOTPVerify(String txnId, String name, String gender, String dob, String requestId) throws JsonProcessingException {
        String timeStamp = getCurrentSimpleTimestamp();

        Map<String, Object> content = new HashMap<>();
        Map<String, Object> credential = new HashMap<>();
        Map<String, Object> demographic = new HashMap<>();

        demographic.put("name", name);
        demographic.put("gender",gender);
        demographic.put("dateOfBirth",dob);

        content.put("requestId", requestId);
        content.put("timestamp", timeStamp);
        content.put("transactionId", txnId);
        credential.put("demographic",demographic);
        content.put("credential",credential);

        AbdmIdVerify entity = abdmIdVerifyRepository.findByTxnId(txnId).get();
        entity.setVerifyRequestId(requestId);
        abdmIdVerifyRepository.save(entity);

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(content);

        return webClient.post().uri("https://dev.abdm.gov.in/gateway/v0.5/users/auth/confirm")
                .header("Authorization","Bearer "+token)
                .header("accept", "*/*")
                .header("X-CM-ID", "sbx")
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(requestBody.toString()))
                .retrieve()
                .onStatus(HttpStatusCode::isError,clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public String getLgdStatesList(){
        return webClient.get().uri("https://hpridsbx.abdm.gov.in/api/v1/ha/lgd/states")
                .header("Authorization","Bearer "+token)
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
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

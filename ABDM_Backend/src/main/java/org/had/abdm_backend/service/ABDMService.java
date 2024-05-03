package org.had.abdm_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.had.abdm_backend.entity.AbdmIdVerify;
import org.had.abdm_backend.entity.ConsentRequest;
import org.had.abdm_backend.exception.MyWebClientException;
import org.had.abdm_backend.repository.AbdmIdVerifyRepository;
import org.had.abdm_backend.repository.ConsentRequestRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ABDMService {
    private static final Logger log = LoggerFactory.getLogger(ABDMService.class);
    @Autowired
    private WebClient webClient;

    @Getter
    public String token;

    @Autowired
    private AbdmIdVerifyRepository abdmIdVerifyRepository;

    @Autowired
    private ConsentRequestRepository consentRequestRepository;

    @Autowired
    private RabbitMqService rabbitMqService;

    public String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public LocalDate todayDate() {
        return LocalDate.now();
    }

    public String generateAadharOTPHPR(String hprId) throws JsonProcessingException {
        var values = new HashMap<String, String>() {{
            put("idType", "hpr_id");
            put("domainName", "@hpr.abdm");
            put("authMethod", "AADHAAR_OTP");
            put("hprId", hprId);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);

        return webClient.post().uri("https://hpridsbx.abdm.gov.in/api/v1/auth/init")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+token)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError,clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public String getDoctorDetails(String otp, String txnId) throws JsonProcessingException {

        var values = new HashMap<String, String>() {{
           put("otp", otp);
           put("txnId", txnId);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);

        String responseBody = webClient.post().uri("https://hpridsbx.abdm.gov.in/api/v1/auth/confirmWithAadhaarOtp")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+token)
                .body(BodyInserters.fromValue(requestBody))
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
        String requestBody;
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
        requester.put("id", requesterId);

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


//    M2 API's from here

    public String linkCareContext(String appointmentId, String accessToken,String date, String patientId,String patientName, String hospitalId) {
        String timeStamp = getCurrentSimpleTimestamp();
        String requestId = generateUUID();
        String display = "OP Consultation on " + date;

        Map<String, String> careContexts = new HashMap<>();
        careContexts.put("referenceNumber", hospitalId+"."+date+"."+appointmentId);
        careContexts.put("display", display);

        Map<String, Object> patient = new HashMap<>();
        patient.put("referenceNumber" , patientId);
        patient.put("display" , patientName +":" + display);
        patient.put("careContexts" , List.of(careContexts));

        Map<String, Object> link = new HashMap<>();
        link.put("accessToken", accessToken);
        link.put("patient", patient);

        Map<String, Object> content = new HashMap<>();
        content.put("requestId", requestId);
        content.put("timestamp", timeStamp);
        content.put("link",link);
System.out.println(content);
        var objectMapper = new ObjectMapper();
        String requestBody = null;
        try {
            requestBody = objectMapper.writeValueAsString(content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return webClient.post().uri("https://dev.abdm.gov.in/gateway/v0.5/links/link/add-contexts")
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("X-CM-ID", "sbx")
                .header("accept", "*/*")
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError,clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class)
                .block();


    }
    
    public String consentInit(JsonNode jsonNode) throws JsonProcessingException{
        System.out.println("Consent Init Service");
        Map<String, String> purpose_map = new HashMap<>();
        purpose_map.put("CAREMGT","Care Management");
        purpose_map.put("BTG","Break the Glass");
        purpose_map.put("PUBHLTH","Public Health");
        purpose_map.put("HPAYMT","Healthcare Payment");
        purpose_map.put("DSRCH","Disease Specific Healthcare Research");
        purpose_map.put("PatRQT","Self Requested");

        String purpose_code = jsonNode.get("purpose_code").asText();
        String patient_id = jsonNode.get("patient_id").asText();
        String hiu_id = jsonNode.get("hiu_id").asText();
        String hiu_name = jsonNode.get("hiu_name").asText();
        String requester_name = jsonNode.get("requester_name").asText();
        String identifier_value = jsonNode.get("identifier_value").asText();
        String permission_from = jsonNode.get("permission_from").asText();
        String permission_to = jsonNode.get("permission_to").asText();
        String data_erase_at = jsonNode.get("data_erase_at").asText();
        String request_id = jsonNode.get("request_id").asText();

        Map<String, Object> data = new HashMap<>();
        data.put("requestId",request_id);
        data.put("timestamp", getISOTimestamp());

        Map<String, Object> consent = new HashMap<>();
        Map<String, Object> purpose = new HashMap<>();
        purpose.put("text", purpose_map.get(purpose_code));
        purpose.put("code", purpose_code);
        purpose.put("refUri", "medisync.com");

        Map<String, String> patient = new HashMap<>();
        patient.put("id", patient_id);

        Map<String, Object> hiu = new HashMap<>();
        hiu.put("id", hiu_id);
        hiu.put("name", hiu_name);

        Map<String, Object> requester = new HashMap<>();
        requester.put("name", requester_name);

        Map<String, String> identifier = new HashMap<>();
        identifier.put("type", "REGNO");
        identifier.put("value", identifier_value);
        identifier.put("system", "https://www.nmc.org.in");
        requester.put("identifier", identifier);

        consent.put("purpose", purpose);
        consent.put("patient", patient);
        consent.put("hiu", hiu);
        consent.put("requester", requester);
        consent.put("hiTypes", jsonNode.get("hi_type"));

        Map<String, Object> permission = new HashMap<>();
        permission.put("accessMode", "VIEW");

        Map<String, String> dateRange = new HashMap<>();
        dateRange.put("from", permission_from);
        dateRange.put("to", permission_to);
        permission.put("dateRange", dateRange);
        permission.put("dataEraseAt", data_erase_at);

        Map<String, Object> frequency = new HashMap<>();
        frequency.put("unit", "MONTH");
        frequency.put("value", 12);
        frequency.put("repeats", 12);
        permission.put("frequency", frequency);

        consent.put("permission", permission);
        data.put("consent", consent);

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(data);

        String routing_key = jsonNode.get("routing_key").asText();
        ConsentRequest consentRequest = new ConsentRequest();
        consentRequest.setRouting_key(routing_key);
        consentRequest.setRequest_id(request_id);
        consentRequestRepository.save(consentRequest);


        return webClient.post().uri("https://dev.abdm.gov.in/gateway/v0.5/consent-requests/init")
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

    public String consentStatus(JsonNode jsonNode) throws JsonProcessingException{
        log.info("Consent Status Service");
        String consent_request_id = jsonNode.get("consent_request_id").asText();
        String requestId = jsonNode.get("request_id").asText();

        Map<String, String> data = new HashMap<>();
        data.put("requestId", requestId);
        data.put("timestamp",getISOTimestamp());
        data.put("consentRequestId",consent_request_id);

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(data);

        return webClient.post().uri("https://dev.abdm.gov.in/gateway/v0.5/consent-requests/status")
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

    public String consentFetch(JsonNode jsonNode) throws JsonProcessingException {
        log.info("Consent Fetch Service");
        String consent_id = jsonNode.get("artefactId").asText();
        Map<String, String> data = new HashMap<>();
        data.put("requestId",UUID.randomUUID().toString());
        data.put("timestamp",getISOTimestamp());
        data.put("consentId",consent_id);

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(data);

        return webClient.post().uri("https://dev.abdm.gov.in/gateway/v0.5/consents/fetch")
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

    public String hipOnNotify(String consentId, String requestId) throws JsonProcessingException {
        System.out.println("Consent hip on-notify Service");
        String consent_id = consentId;
        String request_id = requestId;

        Map<String, Object> data = new HashMap<>();

        data.put("requestId", UUID.randomUUID().toString());
        data.put("timestamp", getCurrentSimpleTimestamp());

        Map<String, Object> acknowledgementMap = new HashMap<>();
        acknowledgementMap.put("status", "OK");
        acknowledgementMap.put("consentId", consent_id);

        Map<String, String> respMap = new HashMap<>();
        respMap.put("requestId", request_id);

        data.put("acknowledgement", acknowledgementMap);
        data.put("resp", respMap);

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(data);

        return webClient.post().uri("https://dev.abdm.gov.in/gateway/v0.5/consents/hip/on-notify")
                .header("Authorization","Bearer "+token)
//                .header("accept", "*/*")
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

    public String HIUOnNotify(String requestId, String consentRequestId) {
        System.out.println("Consent HIU on-notify Service");
        Map<String, Object> data = new HashMap<>();

        data.put("requestId", UUID.randomUUID().toString());
        data.put("timestamp", getCurrentSimpleTimestamp());

        Map<String, Object> acknowledgement = new HashMap<>();
        acknowledgement.put("status", "OK");
        acknowledgement.put("consentId", consentRequestId);

        Map<String, String> resp = new HashMap<>();
        resp.put("requestId", requestId);

        data.put("acknowledgement", acknowledgement);
        data.put("resp", resp);

        var objectMapper = new ObjectMapper();
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return webClient.post().uri("https://dev.abdm.gov.in/gateway/v0.5/consents/hiu/on-notify")
                .header("Authorization","Bearer "+token)
//                .header("accept", "*/*")
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

    public String hipOnRequest(String txnId, String requestId) throws JsonProcessingException{
        String transaction_id = txnId;
        String req_id = requestId;

        Map<String,Object> root = new HashMap<>();
        root.put("requestId",UUID.randomUUID().toString());
        root.put("timestamp", getISOTimestamp());

        Map<String, Object> hiRequestMap = new HashMap<>();
        hiRequestMap.put("transactionId", transaction_id);
        hiRequestMap.put("sessionStatus", "ACKNOWLEDGED");

        root.put("hiRequest",hiRequestMap);
        Map<String, String> respMap = new HashMap<>();
        respMap.put("requestId", req_id);

        root.put("resp", respMap);

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(root);

        return webClient.post().uri("https://dev.abdm.gov.in/gateway/v0.5/health-information/hip/on-request")
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

    public String healthInfoNotify(JsonNode jsonNode) throws  JsonProcessingException{
        String transaction_id = jsonNode.get("transactionId").asText();
        String consent_id = jsonNode.get("consentId").asText();
        String hip_id = jsonNode.get("hipId").asText();
        JsonNode statusResponses = jsonNode.get("statusResponses");

        Map<String, Object> rootMap = new HashMap<>();

        rootMap.put("requestId", UUID.randomUUID().toString());
        rootMap.put("timestamp", getISOTimestamp());

        Map<String, Object> notificationMap = new HashMap<>();
        notificationMap.put("transactionId", transaction_id);
        notificationMap.put("consentId", consent_id);
        notificationMap.put("doneAt", getISOTimestamp());

        Map<String, String> notifierMap = new HashMap<>();
        notifierMap.put("type", "HIP");
        notifierMap.put("id", hip_id);

        notificationMap.put("notifier", notifierMap);

        Map<String, Object> statusNotificationMap = new HashMap<>();
        statusNotificationMap.put("sessionStatus", "TRANSFERRED");
        statusNotificationMap.put("hipId", hip_id);



        statusNotificationMap.put("statusResponses", statusResponses);

        notificationMap.put("statusNotification", statusNotificationMap);

        rootMap.put("notification", notificationMap);

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(rootMap);

        return webClient.post().uri("https://dev.abdm.gov.in/gateway/v0.5/health-information/notify")
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

    public String dataPush(JsonNode jsonNode) throws  JsonProcessingException{
        String expiry = jsonNode.get("expiry").asText();
        String keyval = jsonNode.get("keyval").asText();
        String nonce = jsonNode.get("nonce").asText();
        String transaction_id = jsonNode.get("transactionId").asText();
        String content = jsonNode.get("content").asText();


        Map<String, Object> rootMap = new HashMap<>();

        // Basic properties
        rootMap.put("pageNumber", 0);
        rootMap.put("pageCount", 0);
        rootMap.put("transactionId", transaction_id);

        List<Map<String, String>> entries = new ArrayList<>();
        Map<String, String> entry = new HashMap<>();
        entry.put("content", content);
        entry.put("media", "application/fhir+json");
        entry.put("checksum", "MD5");
        entry.put("careContextReference", "ref_1");
        entries.add(entry);

        rootMap.put("entries", entries);

        Map<String, Object> keyMaterialMap = new HashMap<>();
        keyMaterialMap.put("cryptoAlg", "ECDH");
        keyMaterialMap.put("curve", "Curve25519");

        Map<String, String> dhPublicKeyMap = new HashMap<>();
        dhPublicKeyMap.put("expiry", expiry);
        dhPublicKeyMap.put("parameters", "Curve25519/32byte random key");
        dhPublicKeyMap.put("keyValue", keyval);

        keyMaterialMap.put("dhPublicKey", dhPublicKeyMap);
        keyMaterialMap.put("nonce", nonce);

        rootMap.put("keyMaterial", keyMaterialMap);

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(rootMap);

        return webClient.post().uri("https://webhook.site/8905738a-9ae9-406b-8d4d-ee6c5abf63f8/data/push")
                .header("Authorization","Bearer "+token)
//                .header("accept", "*/*")
//                .header("X-CM-ID", "sbx")
//                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(requestBody.toString()))
                .retrieve()
                .onStatus(HttpStatusCode::isError,clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }
    public String healthInfoCmRequest(JsonNode jsonNode) throws  JsonProcessingException{
        String requestId = jsonNode.get("requestId").asText();
        String consent_id = jsonNode.get("consentId").asText();
        String from = jsonNode.get("from").asText();
        String to = jsonNode.get("to").asText();
        String expiry = jsonNode.get("expiry").asText();
        String keyval = jsonNode.get("keyval").asText();
        String nonce = jsonNode.get("nonce").asText();
        String dataPushUrl = jsonNode.get("dataPushUrl").asText();

        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("requestId", requestId);
        rootMap.put("timestamp", getISOTimestamp());

        Map<String, Object> hiRequestMap = new HashMap<>();

        Map<String, String> consentMap = new HashMap<>();
        consentMap.put("id", consent_id);
        hiRequestMap.put("consent", consentMap);

        Map<String, String> dateRangeMap = new HashMap<>();
        dateRangeMap.put("from", from);
        dateRangeMap.put("to", to);
        hiRequestMap.put("dateRange", dateRangeMap);

        hiRequestMap.put("dataPushUrl", dataPushUrl);

        Map<String, Object> keyMaterialMap = new HashMap<>();
        keyMaterialMap.put("cryptoAlg", "ECDH");
        keyMaterialMap.put("curve", "Curve25519");

        Map<String, String> dhPublicKeyMap = new HashMap<>();
        dhPublicKeyMap.put("expiry", expiry);
        dhPublicKeyMap.put("parameters", "Curve25519/32byte random key");
        dhPublicKeyMap.put("keyValue",keyval);

        keyMaterialMap.put("dhPublicKey", dhPublicKeyMap);

        keyMaterialMap.put("nonce",nonce);

        hiRequestMap.put("keyMaterial", keyMaterialMap);

        rootMap.put("hiRequest", hiRequestMap);

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(rootMap);

        return webClient.post().uri("https://dev.abdm.gov.in/gateway/v0.5/health-information/cm/request")
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

    public String registerFacility(JsonNode jsonNode) {
//        This can be CHanged
        String hipName = jsonNode.get("facilityName").asText();

        String facilityName = jsonNode.get("facilityName").asText();
        String facilityId = jsonNode.get("facilityId").asText();
        String bridgeId = "SBX_004922";

        Map<String, Object> HRP = new HashMap<>();
        HRP.put("bridgeId", bridgeId);
        HRP.put("hipName", hipName);
        HRP.put("type", "HIP");
        HRP.put("active", true);

        Map<String, Object> content = new HashMap<>();
        content.put("facilityId",facilityId);
        content.put("facilityName",facilityName);
        content.put("HRP", List.of(HRP));

        var objectMapper = new ObjectMapper();
        String requestBody = null;
        try {
            requestBody = objectMapper.writeValueAsString(content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return webClient.post().uri("https://facilitysbx.abdm.gov.in/v1/bridges/MutipleHRPAddUpdateServices")
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
//                .header("X-CM-ID", "sbx")
                .header("accept", "application/json")
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError,clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class)
                .block();

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

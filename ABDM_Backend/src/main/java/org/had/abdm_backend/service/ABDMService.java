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

import java.util.HashMap;

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
                .bodyToMono(String.class).block();
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

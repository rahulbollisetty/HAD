package org.had.consentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.had.consentservice.entity.ConsentArtefact;
import org.had.consentservice.entity.ConsentRequest;
import org.had.consentservice.exception.MyWebClientException;
import org.had.consentservice.repository.ConsentArtefactRepository;
import org.had.consentservice.repository.ConsentRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class ConsentService {

    @Autowired
    private SSEService sseService;

    @Autowired
    private WebClient webClient;

    @Value("${routingKey.name}")
    private String routingKey;

    @Autowired
    private ConsentRequestRepository consentRequestRepository;

    @Autowired
    private ConsentArtefactRepository consentArtefactRepository;

    public ResponseEntity<SseEmitter> consentInit(JsonNode jsonNode) {
        System.out.println("consentInit Service");
        String patient_id = jsonNode.get("patient_id").asText();
        String hiu_id = jsonNode.get("hiu_id").asText();
        String hiu_name = jsonNode.get("hiu_name").asText();
        String requester_name = jsonNode.get("requester_name").asText();
        String identifier_value = jsonNode.get("identifier_value").asText();
        String permission_from = jsonNode.get("permission_from").asText();
        String permission_to = jsonNode.get("permission_to").asText();
        String data_erase_at = jsonNode.get("data_erase_at").asText();
        String purpose_code = jsonNode.get("purpose_code").asText();
        JsonNode hi_type = jsonNode.get("hi_type");
        String requestId = UUID.randomUUID().toString();
        var values = new HashMap<String, Object>() {{
            put("patient_id", patient_id);
            put("hiu_id", hiu_id);
            put("hiu_name", hiu_name);
            put("requester_name", requester_name);
            put("identifier_value", identifier_value);
            put("permission_from", permission_from);
            put("permission_to", permission_to);
            put("data_erase_at", data_erase_at);
            put("purpose_code", purpose_code);
            put("routing_key", routingKey);
            put("hi_type", hi_type);
            put("request_id",requestId);
        }};
        String hiTypeString = "";
        if (hi_type != null && hi_type.isArray()) {
            ArrayNode hiTypeArray = (ArrayNode) hi_type;
            List<String> hiTypes = new ArrayList<>();
            hiTypeArray.forEach(type -> hiTypes.add(type.asText()));
            hiTypeString = String.join(",", hiTypes);
        }
        ConsentRequest consentRequest = new ConsentRequest();
        consentRequest.setRequest_id(requestId);
        consentRequest.setRequester_name(requester_name);
        consentRequest.setPatient_id(patient_id);
        consentRequest.setPurpose_code(purpose_code);
        consentRequest.setData_erase_at(data_erase_at);
        consentRequest.setPermission_from(permission_from);
        consentRequest.setPermission_to(permission_to);
        consentRequest.setIdentifier_value(identifier_value);
        consentRequest.setData_erase_at(data_erase_at);
        consentRequest.setPurpose_code(purpose_code);
        consentRequest.setHi_type(hiTypeString);
        consentRequestRepository.save(consentRequest);

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("requestBody: " + requestBody);
        SseEmitter sseEmitter = sseService.createSseEmitter(requestId);
        try {
            webClient.post().uri("http://127.0.0.1:9008/abdm/consent/consentInit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(requestBody))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value()))))
                    .bodyToMono(String.class).block();
            return ResponseEntity.ok(sseEmitter);
        }
        catch (MyWebClientException e){
            sseService.sendErrorMessage(e.getMessage(),e.getStatus(),sseEmitter);
            return ResponseEntity.badRequest().body(sseEmitter);
        }
    }

    public String consentStatus(JsonNode jsonNode) {
        System.out.println("consentStatus Service");
        String consent_request_id = jsonNode.get("consent_request_id").asText();
        Optional<ConsentRequest> optionalConsentRequest = consentRequestRepository.findByConsent_id(consent_request_id);
        if (optionalConsentRequest.isPresent()) {
            String requestId = UUID.randomUUID().toString();
            var values = new HashMap<String, Object>();
            values.put("consent_request_id", consent_request_id);
            values.put("request_id", requestId);

            String requestBody = null;
            var objectMapper = new ObjectMapper();
            try {
                requestBody = objectMapper.writeValueAsString(values);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            SseEmitter sseEmitter = sseService.createSseEmitter(requestId);
            try {
                return webClient.post().uri("http://127.0.0.1:9008/abdm/consent/consentStatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(requestBody))
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value()))))
                        .bodyToMono(String.class).block();
            }
            catch (MyWebClientException e) {
                sseService.sendErrorMessage(e.getMessage(), e.getStatus(), sseEmitter);
                return "Error: " + e.getMessage();
            }
        } else {
            return "Not found";
        }
    }

    public void consentOnStatus(JsonNode jsonNode) {
        System.out.println("Inside Consent On Status Service");
        String requestId = jsonNode.get("response").get("requestId").asText();
        String consentRequestId = jsonNode.get("consentRequest").get("id").asText();
        ConsentRequest consentRequest = consentRequestRepository.findByConsent_id(consentRequestId).get();
        if(jsonNode.hasNonNull("error")){
            return;
        }else {
            try {
                String status = jsonNode.get("consentRequest").get("status").asText();
                if(status.equals("GRANTED")) {
                    consentRequest.setStatus("GRANTED");
                    consentRequestRepository.save(consentRequest);
                    JsonNode consentArtefactsArray = jsonNode.get("consentArtefacts");
                    if (consentArtefactsArray != null && consentArtefactsArray.isArray()) {
                        for (JsonNode artefact : consentArtefactsArray) {
                            ConsentArtefact consentArtefact = new ConsentArtefact();
                            consentArtefact.setConsentRequest(consentRequest);
                            String artefactId = artefact.get("id").asText();
                            consentArtefact.setConsent_artefact(artefactId);
                            consentArtefactRepository.save(consentArtefact);
                        }
                    }
                }
                else if(status.equals("REVOKED")) {
                    consentRequestRepository.delete(consentRequest);
                }
                else if(status.equals("DENIED")) {
                    consentRequestRepository.delete(consentRequest);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void consentOnNotify(JsonNode jsonNode) {
        System.out.println("Inside Consent On Notify HIU Service");
        String consentRequestId = jsonNode.get("notification").get("consentRequestId").asText();
        String requestId = jsonNode.get("requestId").asText();
        ConsentRequest consentRequest = consentRequestRepository.findByConsent_id(consentRequestId).get();
        if(jsonNode.hasNonNull("error")){
            return;
        }
        else {
            try {
                String status = jsonNode.get("notification").get("status").asText();
                if(status.equals("GRANTED")) {
                    consentRequest.setStatus("GRANTED");
                    consentRequestRepository.save(consentRequest);
                    JsonNode consentArtefactsArray = jsonNode.get("consentArtefacts");
                    if (consentArtefactsArray != null && consentArtefactsArray.isArray()) {
                        notify(requestId, consentRequestId);
                        for (JsonNode artefact : consentArtefactsArray) {
                            ConsentArtefact consentArtefact = new ConsentArtefact();
                            consentArtefact.setConsentRequest(consentRequest);
                            String artefactId = artefact.get("id").asText();
                            consentArtefact.setConsent_artefact(artefactId);
                            consentArtefactRepository.save(consentArtefact);
                            consentFetch(artefactId);
                        }

                    }
                }
                else if(status.equals("REVOKED")) {
                    JsonNode consentArtefactsArray = jsonNode.get("consentArtefacts");
                    if (consentArtefactsArray != null && consentArtefactsArray.isArray()) {
                        for (JsonNode artefact : consentArtefactsArray) {
                            ConsentArtefact consentArtefact = consentArtefactRepository.findConsentArtefactBy(artefact.get("id").asText()).get();
                            consentArtefactRepository.delete(consentArtefact);
//                            delete data from care-context table as well
                        }
                    }

                    Optional<ConsentArtefact> consentArtefacts = consentArtefactRepository.findAllByConsentRequest_ConsentId(consentRequestId);
                    if (!consentArtefacts.isPresent()) {
                        consentRequestRepository.delete(consentRequest);
                    }
                }
                else if(status.equals("DENIED")) {
                    consentRequestRepository.delete(consentRequest);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String notify(String requestId, String consentRequestId) {
        System.out.println("Consent on-notify Hiu Service");
        var values = new HashMap<String, Object>();
        values.put("requestID", requestId);
        values.put("consentRequestId", consentRequestId);

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri("http://127.0.0.1:9008/abdm/consent/HIUOnNotify")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value()))))
                .bodyToMono(String.class).block();
    }

    public String consentFetch(String artefactId) {
        System.out.println("fetch Service");
        var values = new HashMap<String, Object>();
        values.put("artefactId", artefactId);

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri("http://127.0.0.1:9008/abdm/consent/consentFetch")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value()))))
                .bodyToMono(String.class).block();
    }
}

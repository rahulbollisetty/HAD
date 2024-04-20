package org.had.consentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.had.consentservice.entity.CareContexts;
import org.had.consentservice.entity.ConsentArtefact;
import org.had.consentservice.entity.ConsentRequest;
import org.had.consentservice.exception.MyWebClientException;
import org.had.consentservice.repository.CareContextRepository;
import org.had.consentservice.repository.ConsentArtefactRepository;
import org.had.consentservice.repository.ConsentRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class ConsentService {

    private static final Logger log = LoggerFactory.getLogger(ConsentService.class);
    @Autowired
    private SSEService sseService;

    @Autowired
    private WebClient webClient;

    @Value("${routingKey.name}")
    private String routingKey;

    @Value("${hospital.Id}")
    private String hospitalId;

    @Autowired
    private ConsentRequestRepository consentRequestRepository;

    @Autowired
    private ConsentArtefactRepository consentArtefactRepository;

    @Autowired
    private CareContextRepository careContextRepository;

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

        String requestBody;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
//        System.out.println("requestBody: " + requestBody);
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

            String requestBody;
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
                    System.out.println(jsonNode.get("consentRequest"));
                    JsonNode consentArtefactsArray = jsonNode.get("consentRequest").get("consentArtefacts");
                    if (consentArtefactsArray != null && consentArtefactsArray.isArray()) {
                        for (JsonNode artefact : consentArtefactsArray) {
                            if(!consentArtefactRepository.findByConsentArtefact(artefact.get("id").asText()).isPresent()) {
                                ConsentArtefact consentArtefact = new ConsentArtefact();
                                consentArtefact.setConsentRequest(consentRequest);
                                String artefactId = artefact.get("id").asText();
                                consentArtefact.setConsentArtefact(artefactId);
                                consentArtefactRepository.save(consentArtefact);
                                consentFetch(artefactId);
                            }
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
        ConsentRequest consentRequest = consentRequestRepository.findByConsent_id(consentRequestId).orElseThrow(() -> {log.error("Consent Id not found");
            return null;
        });

        if(jsonNode.hasNonNull("error")){
            log.error("Error: {}", jsonNode.get("error").asText());
            return;
        }
        else {
            try {
                String status = jsonNode.get("notification").get("status").asText();
                if(status.equals("GRANTED")) {
                    consentRequest.setStatus("GRANTED");
                    consentRequestRepository.save(consentRequest);
                    JsonNode consentArtefactsArray = jsonNode.get("notification").get("consentArtefacts");
//                  notify(requestId, consentRequestId);
                    if(consentArtefactsArray != null && consentArtefactsArray.isArray()) {
                        for (JsonNode artefact : consentArtefactsArray) {
                            ConsentArtefact consentArtefact = new ConsentArtefact();
                            consentArtefact.setConsentRequest(consentRequest);
                            String artefactId = artefact.get("id").asText();
                            consentArtefact.setConsentArtefact(artefactId);
                            consentArtefactRepository.save(consentArtefact);
                        consentFetch(artefactId);
                        }
                    }
                }
                else if(status.equals("REVOKED")) {
                    JsonNode consentArtefactsArray = jsonNode.get("notification").get("consentArtefacts");
                    if (consentArtefactsArray != null && consentArtefactsArray.isArray()) {
                        for (JsonNode artefact : consentArtefactsArray) {
                            // get all cc and remove content from mongo
                            Optional<ConsentArtefact> consentArtefact = consentArtefactRepository.findByConsentArtefact(artefact.get("id").asText());
                            consentArtefact.ifPresent(value -> consentArtefactRepository.delete(value));
//                            delete data from care-context table as well
                        }
                    }

//                    List<ConsentArtefact> consentArtefactList = consentArtefactRepository.findAllByConsentRequest(consentRequest);
//                    if (consentArtefactList.isEmpty()) {
//                        consentRequestRepository.delete(consentRequest);
//                    }
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


    @Transactional
    public void consentOnFetch(JsonNode jsonNode) {

        String status = jsonNode.get("consent").get("status").asText();
        JsonNode consentDetail = jsonNode.get("consent").get("consentDetail");
        if(jsonNode.hasNonNull("error")){
            log.error("Error: {}", jsonNode.get("error").asText());
            return;
        }
        if(status.equals("GRANTED")) {
            String hipId = consentDetail.get("hip").get("id").asText();
            String consentArtefactId = consentDetail.get("consentId").asText();
            Optional<ConsentArtefact> consentArtefactOptional = consentArtefactRepository.findByConsentArtefact(consentArtefactId);

            if(!consentArtefactOptional.isPresent()) {
                log.error("ON-FETCH CONSENT ARTEFACT not found");
                return;
            }
            ConsentArtefact consentArtefact = consentArtefactOptional.get();
            if(!hipId.equals(hospitalId)){
                JsonNode careContextList = consentDetail.get("careContexts");

                if (careContextList != null && careContextList.isArray()) {
                    for (JsonNode artefact : careContextList) {
                        System.out.println(consentArtefactRepository.existsByConsentIdAndCareContexts(consentArtefact.getConsentRequest().getId()
                                ,artefact.get("careContextReference").asText(),
                                hipId));
                        if(!consentArtefactRepository.existsByConsentIdAndCareContexts(consentArtefact.getConsentRequest().getId()
                                ,artefact.get("careContextReference").asText(),
                                hipId)){
                            String ccRef = artefact.get("careContextReference").asText();

                            System.out.println(ccRef);
                            CareContexts careContexts = new CareContexts();
                            careContexts.setHipId(hipId);
                            careContexts.setHipName(consentDetail.get("hip").get("name").asText());
                            careContexts.setPatientSbx(consentDetail.get("patient").get("id").asText());
                            careContexts.setPatientReference(artefact.get("patientReference").asText());
                            careContexts.setCareContextReference(ccRef);
                            careContexts.setSignature(jsonNode.get("consent").get("signature").asText());

                            CareContexts savedCareContexts = careContextRepository.save(careContexts);

                            // Add the saved CareContexts entity to the ConsentArtefact entity
                            consentArtefact.getCareContexts().add(savedCareContexts);

                            // Save the updated ConsentArtefact entity, which cascades the save operation to CareContexts
                            consentArtefactRepository.save(consentArtefact);
                        }
                        else {
                            consentArtefactRepository.delete(consentArtefact);
                            return;
                        }
                    }
                }
            }
            else {
                consentArtefactRepository.delete(consentArtefact);
                return;
            }
        }



    }
}

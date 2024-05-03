package org.had.patientservice.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.had.accountservice.exception.MyWebClientException;
import org.had.patientservice.entity.AppointmentDetails;
import org.had.patientservice.entity.CareContexts;
import org.had.patientservice.entity.ConsentDetails;
import org.had.patientservice.entity.PatientDetails;
import org.had.patientservice.repository.AppointmentRepository;
import org.had.patientservice.repository.CareContextsRepository;
import org.had.patientservice.repository.ConsentDetailRepository;
import org.had.patientservice.repository.PatientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class ConsentService {

    @Autowired
    private ConsentDetailRepository consentDetailRepository;

    @Autowired
    private CareContextsRepository careContextsRepository;

    @Value("${hospital.id}")
    private String hospitalId;

    @Autowired
    public FhirService fhirService;

    @Autowired
    private FideliusService fideliusService;

    @Autowired
    private WebClient webClient;

    @Value("${abdm.url}")
    private String abdmUrl;

    private static final String careContetRegexPattern = "hospital\\d+\\.\\d{4}-\\d{2}-\\d{2}\\.\\d+";

    private static final Pattern pattern = Pattern.compile(careContetRegexPattern);

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PatientDetailsRepository patientDetailsRepository;


    @Transactional
    public void consentOnNotifyHIP(JsonNode jsonNode){
        log.info("consentOnNotifyHIP: " + jsonNode.toString());
        String status = jsonNode.get("notification").get("status").asText();
        if(status.equals("GRANTED")) {
            JsonNode consent_details = jsonNode.get("notification").get("consentDetail");
            String hipId = consent_details.get("hip").get("id").asText();
            String consentId = jsonNode.get("notification").get("consentId").asText();
            if (hipId.equals(hospitalId)) {
                try {
                    ConsentDetails consentDetails = new ConsentDetails();
                    String patientId = consent_details.get("patient").get("id").asText();
                    consentDetails.setStatus("GRANTED");
                    consentDetails.setConsentId(consentId);
                    consentDetails.setPatientId(patientId);
                    consentDetails.setPurposeText(consent_details.get("purpose").get("text").asText());
                    consentDetails.setPurposeCode(consent_details.get("purpose").get("code").asText());
                    JsonNode hi_type = consent_details.get("hiTypes");
                    String hiTypeString = "";
                    if (hi_type != null && hi_type.isArray()) {
                        ArrayNode hiTypeArray = (ArrayNode) hi_type;
                        List<String> hiTypes = new ArrayList<>();
                        hiTypeArray.forEach(type -> hiTypes.add(type.asText()));
                        hiTypeString = String.join(",", hiTypes);
                    }
                    consentDetails.setHiTypes(hiTypeString);
                    consentDetails.setPermissionFrom(consent_details.get("permission").get("dateRange").get("from").asText());
                    consentDetails.setPermissionTo(consent_details.get("permission").get("dateRange").get("from").asText());
                    consentDetails.setDataEraseAt(consent_details.get("permission").get("dataEraseAt").asText());

                    JsonNode careContextsList = consent_details.get("careContexts");
                    if (careContextsList != null && careContextsList.isArray()) {
                        for (JsonNode cc : careContextsList) {
                            CareContexts careContexts = new CareContexts();
                            careContexts.setPatientSbx(patientId);
                            careContexts.setPatientReference(cc.get("patientReference").asText());
                            careContexts.setCareContextReference(cc.get("careContextReference").asText());

                            careContexts.getConsentDetails().add(consentDetails);
                            consentDetails.getCareContexts().add(careContexts);
                            careContextsRepository.save(careContexts);
                            consentDetailRepository.save(consentDetails);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                log.warn("Hospital Id is different in Consent HIP Notify");
            }
        }
        else if(status.equals("REVOKED")) {
            Optional<ConsentDetails> consentDetails = consentDetailRepository.findByConsentId(jsonNode.get("notification").get("consentId").asText());
            consentDetails.ifPresent(value -> consentDetailRepository.delete(value));
        }

    }

    public void healthInformationRequestHIP(JsonNode jsonNode) throws IOException, InterruptedException {
        String consentId = jsonNode.get("hiRequest").get("consent").get("id").asText();
        String txnId = jsonNode.get("transactionId").asText();
        String requesterNonce = jsonNode.get("hiRequest").get("keyMaterial").get("nonce").asText();
        String requesterPublicKey = jsonNode.get("hiRequest").get("keyMaterial").get("dhPublicKey").get("keyValue").asText();
        String dataPushUrl = jsonNode.get("hiRequest").get("dataPushUrl").asText();

        JsonNode gkm = fideliusService.generateKeyMaterials();
        String senderNonce = gkm.get("nonce").textValue();
        String senderPrivateKey = gkm.get("privateKey").textValue();
        String senderPublickey = gkm.get("x509PublicKey").textValue();

        ObjectMapper objectMapper = new ObjectMapper();
        List<JsonNode> jsonNodeList = new ArrayList<>();

        ConsentDetails consentDetails = consentDetailRepository.findByConsentId(consentId).get();
        Optional<PatientDetails> patientDetails = patientDetailsRepository.findByAbhaAddress(consentDetails.getPatientId());

        Set<CareContexts> careContextsList = careContextsRepository.findByConsentDetails(consentDetails);
        for (CareContexts careContexts : careContextsList) {
//            if(! pattern.matcher(careContexts.getCareContextReference()).matches() ){
//                continue;
//            }
//            else{
//                String[] parts = careContexts.getCareContextReference().split("\\.");
//                String appointmentId = parts[2];
//                AppointmentDetails appointmentDetails = appointmentRepository.findById(Integer.parseInt(appointmentId)).orElse(null);
//                if(appointmentDetails == null){
//                    continue;
//                }
//            }

            String fhirContent = fhirService.connvertCareContextToJsonString(careContexts);
            String encryptedContent = fideliusService.encrypt(fhirContent,senderNonce,requesterNonce,senderPrivateKey,requesterPublicKey);
            JsonNode node = objectMapper.createObjectNode()
                    .put("content", encryptedContent)
                    .put("media","application/fhir+json")
                    .put("checksum","MD5")
                    .put("careContextReference", careContexts.getCareContextReference());
            jsonNodeList.add(node);
        }

        Map<String,Object> dhPublicKey = new HashMap<>();
        dhPublicKey.put("expiry",consentDetails.getDataEraseAt());
        dhPublicKey.put("parameters","Curve25519/32byte random key");
        dhPublicKey.put("keyValue",senderPublickey);

        Map<String,Object> keyMaterial = new HashMap<>();
        keyMaterial.put("cryptoAlg","ECDH");
        keyMaterial.put("curve","Curve25519");
        keyMaterial.put("nonce", senderNonce);
        keyMaterial.put("dhPublicKey",dhPublicKey);

        Map<String,Object> root = new HashMap<>();
        root.put("pageNumber", 0);
        root.put("pageCount", 0);
        root.put("transactionId",txnId);
        root.put("entries", jsonNodeList);
        root.put("keyMaterial",keyMaterial);

        webClient.post().uri(dataPushUrl+"/data/push")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(root))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value()))))
                .bodyToMono(String.class).block();
        healthInfoNotify(consentId,txnId,careContextsList);
    }

    public void healthInfoNotify(String consentId, String txnId, Set<CareContexts> careContextsList){


        Map<String,Object> root = new HashMap<>();
        root.put("transactionId",txnId);
        root.put("consentId", consentId);
        root.put("hipId",hospitalId);

        ObjectMapper objectMapper = new ObjectMapper();
        List<JsonNode> jsonNodeList = new ArrayList<>();

        for (CareContexts careContexts : careContextsList) {
            JsonNode node = objectMapper.createObjectNode()
                    .put("hiStatus", "OK")
                    .put("description", "data sent to datapush url")
                    .put("careContextReference", careContexts.getCareContextReference());
            jsonNodeList.add(node);
        }

        root.put("statusResponses", jsonNodeList);
        webClient.post().uri(abdmUrl+"/abdm/consent/healthInfoNotify")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(root))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value()))))
                .bodyToMono(String.class).block();

    }
}

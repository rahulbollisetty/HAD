package org.had.abdm_backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.had.abdm_backend.entity.ConsentRequest;
import org.had.abdm_backend.repository.ConsentRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Slf4j
@Service
public class ConsentService {

    @Autowired
    private ConsentRequestRepository consentRequestRepository;

    @Autowired
    private RabbitMqService rabbitMqService;

    public void consentRequestOnInit(JsonNode jsonNode) {
        System.out.println("jsonNode: " + jsonNode);
        System.out.println("on-init service");
        String consentRequestId = jsonNode.get("consentRequest").get("id").asText();
        String request_id = jsonNode.get("response").get("requestId").asText();
        ConsentRequest consentRequest = consentRequestRepository.findByRequest_id(request_id).get();
        if(jsonNode.hasNonNull("error")){
            System.err.println("errror has occurred");
            consentRequestRepository.delete(consentRequest);
            return;
        }
        consentRequest.setConsentId(consentRequestId);
        consentRequestRepository.save(consentRequest);
        ((ObjectNode) jsonNode).put("type", "consentOnInit");
        String data = jsonNode.toString();
        System.out.println(data);
        rabbitMqService.sendMessage(data, consentRequest.getRouting_key());
    }

    public void consentRequestOnStatus(JsonNode jsonNode, String hiuId) {
        System.out.println("jsonNode: " + jsonNode);
        System.out.println("hiuId: " + hiuId);
        System.out.println("on-status service");
        String consentStatus = jsonNode.get("consentRequest").get("status").asText();
        String consentRequestId = jsonNode.get("consentRequest").get("id").asText();
        if (consentStatus.equals("GRANTED")) {
            ((ObjectNode) jsonNode).put("type", "consentOnStatus");
            String data = jsonNode.toString();
            System.out.println(data);
            rabbitMqService.sendMessage(data, hiuId+"-consent");
        }
        else if(consentStatus.equals("REVOKED")) {
            Optional<ConsentRequest> consentRequest = consentRequestRepository.findByConsentId(consentRequestId);
            consentRequest.ifPresent(request -> consentRequestRepository.delete(request));
            ((ObjectNode) jsonNode).put("type", "consentOnStatus");
            String data = jsonNode.toString();
            System.out.println(data);
            rabbitMqService.sendMessage(data, hiuId+"-consent");
        }
        else if(consentStatus.equals("DENIED")) {
            Optional<ConsentRequest> consentRequest = consentRequestRepository.findByConsentId(consentRequestId);
            consentRequest.ifPresent(request -> consentRequestRepository.delete(request));
            ((ObjectNode) jsonNode).put("type", "consentOnStatus");
            String data = jsonNode.toString();
            System.out.println(data);
            rabbitMqService.sendMessage(data, hiuId+"-consent");
        }
    }

    public void consentRequestNotifyHIU(JsonNode jsonNode, String hiuID) {
        System.out.println("jsonNode: " + jsonNode);
        System.out.println("hiu notify service");
        String consentStatus = jsonNode.get("notification").get("status").asText();
        String consentRequestId = jsonNode.get("notification").get("consentRequestId").asText();
        if (consentStatus.equals("GRANTED")) {
            ((ObjectNode) jsonNode).put("type", "consentOnNotify");
            String data = jsonNode.toString();
            System.out.println(data);
            rabbitMqService.sendMessage(data, hiuID + "-consent");
        }
        else if(consentStatus.equals("REVOKED")) {
            Optional<ConsentRequest> consentRequest = consentRequestRepository.findByConsentId(consentRequestId);
            consentRequest.ifPresent(request -> consentRequestRepository.delete(request));
            ((ObjectNode) jsonNode).put("type", "consentOnNotify");
            String data = jsonNode.toString();
            System.out.println(data);
            rabbitMqService.sendMessage(data, hiuID + "-consent");
        }
        else if(consentStatus.equals("DENIED")) {
            ((ObjectNode) jsonNode).put("type", "consentOnNotify");
            String data = jsonNode.toString();
            Optional<ConsentRequest> consentRequest = consentRequestRepository.findByConsentId(consentRequestId);
            consentRequest.ifPresent(request -> consentRequestRepository.delete(request));
            System.out.println(data);
            rabbitMqService.sendMessage(data, hiuID + "-consent");
        }
    }
    public void consentArtefactOnFetch(JsonNode jsonNode, String hiuID) {
        System.out.println("jsonNode: " + jsonNode);
        System.out.println("hiu on fetch service");

        if(jsonNode.hasNonNull("error")){
            log.error("errror has occurred");
            return;
        }
        ((ObjectNode) jsonNode).put("type", "consentOnFetch");
        String data = jsonNode.toString();
        System.out.println(data);
        rabbitMqService.sendMessage(data, hiuID + "-consent");

    }

    public void consentRequestNotifyHIP(JsonNode jsonNode, String hipID) {
        System.out.println("jsonNode: " + jsonNode);
        System.out.println("hip notify service");
        ((ObjectNode) jsonNode).put("type", "consentOnNotifyHIP");
        String data = jsonNode.toString();
        System.out.println(data);
        rabbitMqService.sendMessage(data, hipID + "-patient");

//        String consentStatus = jsonNode.get("notification").get("status").asText();
//        String consentRequestId = jsonNode.get("notification").get("consentRequestId").asText();
//        if (consentStatus.equals("GRANTED")) {
//            ((ObjectNode) jsonNode).put("type", "consentOnNotifyHIP");
//            String data = jsonNode.toString();
//            System.out.println(data);
//            rabbitMqService.sendMessage(data, hipID + "-patient");
//        }
//        else if(consentStatus.equals("REVOKED")) {
//            Optional<ConsentRequest> consentRequest = consentRequestRepository.findByConsentId(consentRequestId);
//            consentRequest.ifPresent(request -> consentRequestRepository.delete(request));
//            ((ObjectNode) jsonNode).put("type", "consentOnNotifyHIP");
//            String data = jsonNode.toString();
//            System.out.println(data);
//            rabbitMqService.sendMessage(data, hipID + "-patient");
//        }
//        else if(consentStatus.equals("DENIED")) {
//            ((ObjectNode) jsonNode).put("type", "consentOnNotifyHIP");
//            String data = jsonNode.toString();
//            Optional<ConsentRequest> consentRequest = consentRequestRepository.findByConsentId(consentRequestId);
//            consentRequest.ifPresent(request -> consentRequestRepository.delete(request));
//            System.out.println(data);
//            rabbitMqService.sendMessage(data, hipID + "-patient");
//        }
    }


}

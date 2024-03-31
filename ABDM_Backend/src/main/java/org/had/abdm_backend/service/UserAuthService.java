package org.had.abdm_backend.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.had.abdm_backend.entity.AbdmIdVerify;
import org.had.abdm_backend.repository.AbdmIdVerifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

    @Autowired
    private AbdmIdVerifyRepository abdmIdVerifyRepository;

    @Autowired
    private RabbitMqService rabbitMqService;

    public void userAuthOnInit(JsonNode jsonNode){
        String requestId = jsonNode.get("resp").get("requestId").asText();
        AbdmIdVerify abdmIdVerify = abdmIdVerifyRepository.findByInitRequestId(requestId).get();
        if(!jsonNode.hasNonNull("error")){
            System.err.println("errror has occurred");
            abdmIdVerifyRepository.delete(abdmIdVerify);
        } else if (jsonNode.hasNonNull("auth")) {
            abdmIdVerify.setTxnId(jsonNode.get("auth").get("transactionId").asText());
            abdmIdVerifyRepository.save(abdmIdVerify);
        }
        ((ObjectNode) jsonNode).put("type", "userAuthInit");
        String data = jsonNode.toString();
        System.out.println(data);
        rabbitMqService.sendMessage(data, abdmIdVerify.getRoutingKey());
    }

    public void userAuthOnConfirm(JsonNode jsonNode){
        String requestId = jsonNode.get("resp").get("requestId").asText();
        AbdmIdVerify abdmIdVerify = abdmIdVerifyRepository.findByVerifyRequestId(requestId).get();
        if(!jsonNode.hasNonNull("error")){
            System.err.println("errror has occurred");
        }
        abdmIdVerifyRepository.delete(abdmIdVerify);
        ((ObjectNode) jsonNode).put("type", "userAuthOnConfirm");
        String data = jsonNode.toString();
        System.out.println(data);
        rabbitMqService.sendMessage(data, abdmIdVerify.getRoutingKey());
    }

}

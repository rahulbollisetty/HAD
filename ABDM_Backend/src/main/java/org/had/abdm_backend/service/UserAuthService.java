package org.had.abdm_backend.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.had.abdm_backend.entity.AbdmIdVerify;
import org.had.abdm_backend.repository.AbdmIdVerifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
<<<<<<< HEAD
=======
import java.util.Optional;
>>>>>>> 758bc15 (webhook added)
=======
>>>>>>> 3229706 (sse added and connected with rabbitmq)

@Service
public class UserAuthService {

    @Autowired
    private AbdmIdVerifyRepository abdmIdVerifyRepository;

    @Autowired
    private RabbitMqService rabbitMqService;

    public void userAuthOnInit(JsonNode jsonNode){
        String requestId = jsonNode.get("resp").get("requestId").asText();
        AbdmIdVerify abdmIdVerify = abdmIdVerifyRepository.findByInitRequestId(requestId).get();
        ((ObjectNode) jsonNode).put("type", "userAuthInit");
        String data = jsonNode.toString();
        System.out.println(data);
        rabbitMqService.sendMessage(data, abdmIdVerify.getRoutingKey());
    }

}

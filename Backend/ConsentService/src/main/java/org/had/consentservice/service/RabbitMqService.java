package org.had.consentservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RabbitMqService {

    @Autowired
    private SSEService sseService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "${queue.name}")
    public void receiveJsonData(String jsonData) {
        if (!jsonData.isEmpty()){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode jsonNode = objectMapper.readTree(jsonData);
                String type = jsonNode.get("type").asText();

                switch (type) {
                    case "consentInit":
//                        sseService.sendUserAuthData(jsonData);
                        System.out.println("Received userAuthInit: " + jsonData);
                        break;
                    case "consent":
                        System.out.println("Received userAuthOtpVerify: " + jsonData);
                        break;
                    default:
                        System.out.println("Unknown data type");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


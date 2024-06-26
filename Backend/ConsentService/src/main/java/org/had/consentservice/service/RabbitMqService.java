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

    @Autowired
    private ConsentService consentService;

    @RabbitListener(queues = "${queue.name}")
    public void receiveJsonData(String jsonData) {
        System.out.println("RabbitMQ Service received!");
        if (!jsonData.isEmpty()){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode jsonNode = objectMapper.readTree(jsonData);
                String type = jsonNode.get("type").asText();

                switch (type) {
                    case "consentOnInit":
                        sseService.consentOnInit(jsonNode);
                        System.out.println("Received consentOnInit: " + jsonData);
                        break;
                    case "consentOnStatus" :
                        System.out.println("Received consentOnStatus: " + jsonData);
                        consentService.consentOnStatus(jsonNode);
                        break;
                    case "consentOnNotify":
                        System.out.println("Received consentOnNotify: " + jsonData);
                        consentService.consentOnNotify(jsonNode);
                        break;
                    case "consentOnFetch":
                        System.out.println("Received consentOnFetch: " + jsonData);
                        consentService.consentOnFetch(jsonNode);
                        break;
                    case "hiuOnRequest":
                        System.out.println("Received hiuOnRequest: " + jsonData);
                        consentService.hiuOnRequest(jsonNode);
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


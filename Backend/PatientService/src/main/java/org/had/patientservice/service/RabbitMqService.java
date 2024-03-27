package org.had.patientservice.service;

<<<<<<< HEAD
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
=======
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Value;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
>>>>>>> 758bc15 (webhook added)
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class RabbitMqService {
<<<<<<< HEAD

    @Autowired
    private SSEService sseService;

    @RabbitListener(queues = "${queue.name}")
    public void receiveJsonData(String jsonData) {
        if (!jsonData.isEmpty()){
=======
    @RabbitListener(queues = "${queue.name}")
    public void receiveJsonData(String jsonData) {
        if (!jsonData.isEmpty()){

>>>>>>> 758bc15 (webhook added)
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode jsonNode = objectMapper.readTree(jsonData);
                String type = jsonNode.get("type").asText();

                switch (type) {
                    case "userAuthInit":
<<<<<<< HEAD
                        sseService.sendUserAuthData(jsonData);
                        System.out.println("Received userAuthInit: " + jsonData);
                        break;
                    case "userAuthOtpVerify":
                        System.out.println("Received userAuthOtpVerify: " + jsonData);
                        break;
=======
                        System.out.println("Received userAuthInit: " + jsonData);
                        break;
>>>>>>> 758bc15 (webhook added)
                    default:
                        System.out.println("Unknown data type");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

package org.had.patientservice.service;

<<<<<<< HEAD
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
=======
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
>>>>>>> 3229706 (sse added and connected with rabbitmq)
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class RabbitMqService {
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 3229706 (sse added and connected with rabbitmq)

    @Autowired
    private SSEService sseService;

<<<<<<< HEAD
    @RabbitListener(queues = "${queue.name}")
    public void receiveJsonData(String jsonData) {
        if (!jsonData.isEmpty()){
=======
    @RabbitListener(queues = "${queue.name}")
    public void receiveJsonData(String jsonData) {
        if (!jsonData.isEmpty()){

>>>>>>> 758bc15 (webhook added)
=======
    @RabbitListener(queues = "${queue.name}")
    public void receiveJsonData(String jsonData) {
        if (!jsonData.isEmpty()){
>>>>>>> 3229706 (sse added and connected with rabbitmq)
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode jsonNode = objectMapper.readTree(jsonData);
                String type = jsonNode.get("type").asText();

                switch (type) {
                    case "userAuthInit":
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 3229706 (sse added and connected with rabbitmq)
                        sseService.sendUserAuthData(jsonData);
                        System.out.println("Received userAuthInit: " + jsonData);
                        break;
                    case "userAuthOtpVerify":
                        System.out.println("Received userAuthOtpVerify: " + jsonData);
                        break;
<<<<<<< HEAD
=======
                        System.out.println("Received userAuthInit: " + jsonData);
                        break;
>>>>>>> 758bc15 (webhook added)
=======
>>>>>>> 3229706 (sse added and connected with rabbitmq)
                    default:
                        System.out.println("Unknown data type");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

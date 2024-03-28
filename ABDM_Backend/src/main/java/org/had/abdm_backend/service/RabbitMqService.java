package org.had.abdm_backend.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String message,String routingKey) {
        rabbitTemplate.convertAndSend("abdm", routingKey, message);
        System.out.println("Message sent: " + message);
    }
}

package org.had.abdm_backend.controller;


import com.fasterxml.jackson.databind.JsonNode;
import org.had.abdm_backend.service.ABDMService;
import org.had.abdm_backend.service.RabbitMqService;
import org.had.abdm_backend.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/abdm/webhook")
@CrossOrigin("*")
public class WebhookController {

    @Autowired
    private ABDMService abdmService;

    @Autowired
    private UserAuthService userAuthService;


    @PostMapping("/v0.5/users/auth/on-init")
    public void userAuthOnInit(@RequestBody JsonNode jsonNode){
        userAuthService.userAuthOnInit(jsonNode);
    }

    @PostMapping("/v0.5/users/auth/on-confirm")
    public void userOnConfirm(@RequestBody JsonNode jsonNode){
        userAuthService.userAuthOnConfirm(jsonNode);
    }
}

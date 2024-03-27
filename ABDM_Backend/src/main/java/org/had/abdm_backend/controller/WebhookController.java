package org.had.abdm_backend.controller;


import com.fasterxml.jackson.databind.JsonNode;
import org.had.abdm_backend.service.ABDMService;
import org.had.abdm_backend.service.RabbitMqService;
import org.had.abdm_backend.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/abdm/webhook")
@CrossOrigin("http://localhost:5173")
public class WebhookController {

    @Autowired
    private ABDMService abdmService;

    @Autowired
    private UserAuthService userAuthService;


    @PostMapping("/v0.5/users/auth/on-init")
    public void userAuthOnInit(@RequestBody JsonNode jsonNode){
        userAuthService.userAuthOnInit(jsonNode);
<<<<<<< HEAD
        System.out.println(userAuthService);
=======
>>>>>>> 758bc15 (webhook added)
    }
}

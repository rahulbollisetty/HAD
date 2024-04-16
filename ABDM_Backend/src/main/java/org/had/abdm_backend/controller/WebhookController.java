package org.had.abdm_backend.controller;


import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.had.abdm_backend.service.ABDMService;
import org.had.abdm_backend.service.ConsentService;
import org.had.abdm_backend.service.RabbitMqService;
import org.had.abdm_backend.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/abdm/webhook")
public class WebhookController {

    @Autowired
    private ABDMService abdmService;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private ConsentService consentService;


    @PostMapping("/v0.5/users/auth/on-init")
    public void userAuthOnInit(@RequestBody JsonNode jsonNode){
        userAuthService.userAuthOnInit(jsonNode);
    }

    @PostMapping("/v0.5/users/auth/on-confirm")
    public void userOnConfirm(@RequestBody JsonNode jsonNode){
        userAuthService.userAuthOnConfirm(jsonNode);
    }

    @PostMapping("/api/v3/hiu/consent/request/on-init")
    public void consentRequestOnInit(@RequestBody JsonNode jsonNode){
        System.out.println("Webhook COntroller request-on-init");
        consentService.consentRequestOnInit(jsonNode);
    }

    @PostMapping("/api/v3/hiu/consent/request/on-status")
    public void consentRequestOnStatus(@RequestBody JsonNode jsonNode, HttpServletRequest httpServlet){
        System.out.println("Webhook COntroller on-status");
        String hiuId = httpServlet.getHeader("x-hiu-id");
        System.out.println("webhook on-status");
        consentService.consentRequestOnStatus(jsonNode, hiuId);
    }

    @PostMapping("api/v3/hiu/consent/request/notify")
    public void consentRequestNotifyHIU(@RequestBody JsonNode jsonNode, HttpServletRequest httpServlet){
        System.out.println("Webhook COntroller hiu notify");
        String hiuId = httpServlet.getHeader("x-hiu-id");
        consentService.consentRequestNotifyHIU(jsonNode, hiuId);
    }



}

package org.had.abdm_backend.controller;

import org.had.abdm_backend.service.ABDMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
@CrossOrigin("http://localhost:5173")
public class PatientRegisterApi {
    @Autowired
    private ABDMService abdmService;
}

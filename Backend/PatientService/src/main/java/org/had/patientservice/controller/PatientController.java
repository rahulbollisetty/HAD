package org.had.patientservice.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
@Slf4j
public class PatientController {

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping("/hello")
    public String hello(){
        return "hello";
    }
}

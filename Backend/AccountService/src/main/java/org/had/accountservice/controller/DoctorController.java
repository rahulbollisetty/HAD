package org.had.accountservice.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.had.accountservice.dto.DoctorHPR;
import org.had.accountservice.entity.DoctorDetails;
import org.had.accountservice.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@Slf4j
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @GetMapping(value = "/getAllDoctorList",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllDoctorList(){
        List<DoctorDetails> doctorDetailsList = doctorService.getdoctorDetailsList();
        if(doctorDetailsList.isEmpty())return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("no doctors found");
        return ResponseEntity.ok(doctorDetailsList);
    }
}

package org.had.patientservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import org.had.patientservice.dto.AppointmentDto;
import org.had.patientservice.entity.AppointmentDetails;
import org.had.patientservice.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.util.Date;

import static org.apache.http.client.utils.DateUtils.parseDate;

@RestController
@RequestMapping("/patient/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping(value = "/createAppointment", produces = MediaType.APPLICATION_JSON_VALUE)
    public String createNewAppointment(@Valid @RequestBody AppointmentDto appointmentDto){

        appointmentService.createNewAppointment(appointmentDto);

        return "Successfully added new appointment";

    }

}

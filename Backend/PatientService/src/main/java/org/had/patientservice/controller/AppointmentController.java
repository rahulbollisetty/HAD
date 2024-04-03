package org.had.patientservice.controller;

import org.had.patientservice.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping(value = "/createAppointment", produces = MediaType.APPLICATION_JSON_VALUE)
    public String createNewAppointment(@Valid @RequestBody AppointmentDto appointmentDto){

        appointmentService.createNewAppointment(appointmentDto);

        return "Successfully added new appointment";

    }

}

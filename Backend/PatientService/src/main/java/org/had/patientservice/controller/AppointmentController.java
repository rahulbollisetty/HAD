package org.had.patientservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import org.had.patientservice.dto.AppointmentDto;
import org.had.patientservice.entity.AppointmentDetails;
import org.had.patientservice.entity.OpConsultation;
import org.had.patientservice.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping(value = "/createAppointment", produces = MediaType.APPLICATION_JSON_VALUE)
    public String createNewAppointment(@Valid @RequestBody AppointmentDto appointmentDto){

        appointmentService.createNewAppointment(appointmentDto);

        return "Successfully added new appointment";
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @GetMapping(value = "/getAppointmentDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAppointmentDetails(@RequestParam String id){
        return appointmentService.getAppointmentDetails(Integer.parseInt(id));
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @GetMapping(value = "/getPatientVitals", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPatientVitals(@RequestParam String id) {
        return appointmentService.getPatientVitals(id);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping(value = "/completeAppointment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> completeAppointment(@RequestBody JsonNode jsonNode) {
        return appointmentService.completeAppointment(jsonNode);
    }
}

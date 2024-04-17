package org.had.patientservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import org.had.patientservice.dto.AppointmentDto;
import org.had.patientservice.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping(value = "/getPrescription", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPrescription(@RequestBody JsonNode jsonNode) {
        return appointmentService.getPrescription(jsonNode);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping(value = "/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }
        try {
            appointmentService.saveFile(file.getOriginalFilename(), file.getBytes());
            return ResponseEntity.ok("File uploaded successfully: " + file.getOriginalFilename());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }


    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @GetMapping("files/{fileName:.+}")
    public ResponseEntity<?> serveFile(@PathVariable String fileName) {
        try {
            byte[] fileData = appointmentService.getFile(fileName);
            ByteArrayResource resource = new ByteArrayResource(fileData);

            // Determine the appropriate media type based on file extension
            MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
            if (fileName.endsWith(".pdf")) {
                mediaType = MediaType.APPLICATION_PDF;
            } else if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                mediaType = MediaType.IMAGE_JPEG;
            } // Add more conditions for other file types if needed

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                    .contentType(mediaType)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }


//    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
//    @PostMapping(value = "/uploadImage", produces = MediaType.APPLICATION_JSON_VALUE)
//    public String handleFileUpload( @RequestParam("file") MultipartFile file, @RequestParam("folderName") String folderName) {
//        return appointmentService.handleFileUpload(file, folderName);
//    }
//
//    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
//    @GetMapping( value = "/getImageData", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> getImagesFromFolder(@RequestParam("folderName") String folderName) {
//        System.out.println(folderName);
//        return appointmentService.getImagesFromFolder(folderName);
//    }


}

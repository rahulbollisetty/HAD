package org.had.patientservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.had.patientservice.dto.AppointmentDto;
import org.had.patientservice.entity.AppointmentDetails;
import org.had.patientservice.entity.OpConsultation;
import org.had.patientservice.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @GetMapping(value = "/getAppointmentForDoctor", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAppointmentForDoctor(@RequestParam String id, String name){
        return appointmentService.getAppointmentForDoctor(Integer.parseInt(id), name);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @GetMapping(value = "/getPatientVitals", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPatientVitals(@RequestParam String id) {
        return appointmentService.getPatientVitals(id);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping(value = "/completeAppointment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> completeAppointment(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("requestBody") String jsonBody) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonBody);
            return appointmentService.completeAppointment(jsonNode, file);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @PostMapping(value = "/getPrescription", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPrescription(@RequestBody JsonNode jsonNode) {
        return appointmentService.getPrescription(jsonNode);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @GetMapping(value = "/getAllAppointments", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AppointmentDetails> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }



//    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
//    @PostMapping(value = "/uploadFile")
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body("Please select a file to upload");
//        }
//        String resp = appointmentService.handleFileUpload(file, "1");
//        return ResponseEntity.ok("File uploaded successfully: " + resp);
//    }


    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
    @GetMapping("/getOpData")
    public ResponseEntity<?> serveFile(@RequestParam String id) {

        try {
            OpConsultation opConsultation = appointmentService.getOpConsultation(id);
            String fileName = opConsultation.getFilePath().substring(opConsultation.getFilePath().lastIndexOf('/') + 1);
            byte[] fileData = appointmentService.getFile(opConsultation.getFilePath());
            String fileDataEncoded = Base64.getEncoder().encodeToString(fileData);
            // Determine the appropriate media type based on file extension
            MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
            if (fileName.endsWith(".pdf")) {
                mediaType = MediaType.APPLICATION_PDF;
            } else if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                mediaType = MediaType.IMAGE_JPEG;
            } // Add more conditions for other file types if needed


            // Prepare JSON data
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("fileData", fileDataEncoded); // Use byte array instead of ByteArrayResource
            responseData.put("fileName", fileName);
            responseData.put("fileDescription",opConsultation.getFileDescription());
            responseData.put("observation",opConsultation.getObservations());
            responseData.put("fileMediaType", mediaType.toString());

            // Convert responseData to JSON format
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(responseData);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


//    @GetMapping("/generate-pdf")
//    public String generatePdf(@RequestParam String id) throws Exception {
//        appointmentService.generatePdf(id);
//        return "pdf-generated";
//    }

//    @PreAuthorize("hasAnyAuthority('DOCTOR','STAFF')")
//    @GetMapping( value = "/getImageData", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> getImagesFromFolder(@RequestParam("folderName") String folderName) {
//        System.out.println(folderName);
//        return appointmentService.getImagesFromFolder(folderName);
//    }


}

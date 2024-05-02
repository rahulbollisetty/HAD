package org.had.patientservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.had.accountservice.exception.MyWebClientException;
import org.had.patientservice.dto.PatientDetailsDto;
import org.had.patientservice.entity.*;
import org.had.patientservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class PatientService {

    @Autowired
    private WebClient webClient;

    @Value("${routingKey.name}")
    private String routingKey;

    @Value("${abdm.url}")
    private String abdmUrl;

    @Autowired
    private SSEService sseService;

    @Autowired
    private PatientDetailsRepository patientDetailsRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private OpConsultationRepository opConsultationRepository;

    @Autowired
    private PatientVitalsRepository patientVitalsRepository;

    @Autowired
    private PrescriptionDetailsRepository prescriptionDetailsRepository;

    @Autowired
    private PatientRegistrationLogDetailsRepository patientRegistrationLogDetailsRepository;

    @Autowired
    private RecordDeletionLogDetailsRepository recordDeletionLogDetailsRepository;

    public String requestAadharOtp(String aadhar) {
        var values = new HashMap<String, String>() {{
            put("aadhaar", aadhar);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri(abdmUrl+"/abdm/patient/aadhaarOTPInit")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public String verifyAadharOTP(String otp, String transactionId) {
        var values = new HashMap<String, String>() {{
            put("otp", otp);
            put("transactionId",transactionId);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri(abdmUrl+"/abdm/patient/aadhaarOTPVerify")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();

    }

    public String checkAndMobileOTPinit(String mobile, String txnId) {
        var values = new HashMap<String, String>() {{
            put("mobile", mobile);
            put("txnId",txnId);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri(abdmUrl+"/abdm/patient/checkAndMobileOTPInit")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public String mobileOTPVerify(String otp, String txnId) {
        var values = new HashMap<String, String>() {{
            put("otp", otp);
            put("txnId",txnId);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri(abdmUrl+"/abdm/patient/mobileOTPVerify")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public String createHealthId(String txnId) {
        var values = new HashMap<String, String>() {{
            put("txnId",txnId);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri(abdmUrl+"/abdm/patient/createHealthId")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public String getProfileDetails(String authToken) {
        var values = new HashMap<String, String>() {{
            put("authToken",authToken);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri(abdmUrl+"/abdm/patient/getProfileDetails")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public String healthIdSuggestions(String txnId) {
        var values = new HashMap<String, String>() {{
            put("transactionId",txnId);
        }};
        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri(abdmUrl+"/abdm/patient/healthIdSuggestions")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public String checkPHRAddressExist(String Id) {
        var values = new HashMap<String, String>() {{
            put("Id",Id);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri(abdmUrl+"/abdm/patient/checkPHRAddressExist")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public String createPHRAddress(String phrAddress, String transactionId) {
        var values = new HashMap<String, String>() {{
            put("phrAddress",phrAddress);
            put("transactionId", transactionId);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri(abdmUrl+"/abdm/patient/createPHRAddress")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();

    }

    public ResponseEntity<SseEmitter> userAuthInit(String patientSBXId, String requesterId, String requesterType) throws IOException {
        String requestId = UUID.randomUUID().toString();
        var values = new HashMap<String, String>() {{
            put("patientSBXId", patientSBXId);
            put("requesterId",requesterId);
            put("requesterType",requesterType);
            put("requestId",requestId);
            put("routingKey",routingKey);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        SseEmitter sseEmitter = sseService.createSseEmitter(requestId);
        try{
            String response = webClient.post().uri(abdmUrl+"/abdm/patient/userAuthInit")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
        return ResponseEntity.ok().body(sseEmitter);
        }
        catch (MyWebClientException e){
            sseService.sendErrorMessage(e.getMessage(),e.getStatus(),sseEmitter);
            return ResponseEntity.badRequest().body(sseEmitter);
        }

    }

    public ResponseEntity<SseEmitter> userAuthVerify(String txnId, String name, String gender, String dob) throws IOException {
        String requestId = UUID.randomUUID().toString();
        var values = new HashMap<String, String>() {{
            put("transactionId", txnId);
            put("name",name);
            put("gender",gender);
            put("dob",dob);
            put("requestId",requestId);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        SseEmitter sseEmitter = sseService.createSseEmitter(requestId);
        try{
            String response = webClient.post().uri(abdmUrl+"/abdm/patient/userAuthVerify")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(requestBody))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, clientResponse -> {
                        return clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                    })
                    .bodyToMono(String.class).block();
            return ResponseEntity.ok().body(sseEmitter);
        }
        catch (MyWebClientException e){
            sseService.sendErrorMessage(e.getMessage(),e.getStatus(),sseEmitter);
            return ResponseEntity.badRequest().body(sseEmitter);
        }

    }

    public ResponseEntity<?> savePatient(PatientDetailsDto patientDetailsDto) {
        if (patientDetailsRepository.findByAbhaAddress(patientDetailsDto.getAbhaAddress()).isPresent()) {
            return ResponseEntity.badRequest().body("Abha Address already exists, Patient already exists");
        }

        PatientDetails patient = new PatientDetails();
        patient.setName(patientDetailsDto.getName());
        patient.setAbhaAddress(patientDetailsDto.getAbhaAddress());
        patient.setAbhaNumber(patientDetailsDto.getAbhaNumber());
        patient.setAddress(patientDetailsDto.getAddress());
        patient.setMobileNumber(patientDetailsDto.getMobileNumber());
        patient.setGender(patientDetailsDto.getGender());
        patient.setDob(patientDetailsDto.getDob());
        patient.setEmail(patientDetailsDto.getEmail());
        patient.setBloodGroup(patientDetailsDto.getBloodGroup());
        patient.setOccupation(patientDetailsDto.getOccupation());
        patient.setFamilyMemberName(patientDetailsDto.getFamilyMemberName());
        patient.setRelationship(patientDetailsDto.getRelationship());
        patient.setTown(patientDetailsDto.getTown());
        patient.setPincode(patientDetailsDto.getPincode());
        patient.setState(patientDetailsDto.getState());
        patient.setLinkToken(patientDetailsDto.getLinkToken());
        patientDetailsRepository.save(patient);

        String dataFrom = patientDetailsDto.getDataFrom();
        PatientRegistrationLogDetails patientRegistrationLogDetails = new PatientRegistrationLogDetails();
        if(dataFrom.equals("AbhaVerify")) {
            patientRegistrationLogDetails.setRegistrationMethod("DEMOGRAPHICS");
        }
        patientRegistrationLogDetails.setPatientName(patientDetailsDto.getName());
        LocalDateTime currentDateTime = LocalDateTime.now();
        Date currentDate = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());
        patientRegistrationLogDetails.setGeneratedAt(currentDate);
        patientRegistrationLogDetails.setGeneratedByName(patientDetailsDto.getFacultyName());
        patientRegistrationLogDetails.setRole(patientDetailsDto.getRole());
        patientRegistrationLogDetailsRepository.save(patientRegistrationLogDetails);
        return ResponseEntity.ok().body("Patient Saved");
    }


    public String getLgdStatesList() {
        return webClient.post().uri(abdmUrl+"/abdm/hpr/getLgdStatesList")
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public List<PatientDetails> getPatientDetailsList(){
        return patientDetailsRepository.findAll();
    }

    public PatientDetails getPatientDetails(Integer id){
        return patientDetailsRepository.findById(id).get();
    }

    public ResponseEntity<?> deletePatient(JsonNode jsonNode) {
        Integer patientId = jsonNode.get("patientId").asInt();
        Optional<PatientDetails> patientOptional = patientDetailsRepository.findById(patientId);
        if (patientOptional.isPresent()) {
            PatientDetails patientDetails = patientOptional.get();
            List<AppointmentDetails> appointmentDetails = appointmentRepository.findAllByPatientId(patientDetails);
            for (AppointmentDetails appointment : appointmentDetails) {
                Optional<OpConsultation> opConsultationOptional = opConsultationRepository.findByAppointmentDetails(appointment);
                if(opConsultationOptional.isPresent()) {
                    OpConsultation opConsultation = opConsultationOptional.get();
                    Integer opId = opConsultation.getOp_id();
                    List<PrescriptionDetails> prescriptionDetailsList = prescriptionDetailsRepository.findByOpConsultation(opId);
                    if(prescriptionDetailsList != null) {
                        for(PrescriptionDetails prescriptionDetails : prescriptionDetailsList)
                            prescriptionDetailsRepository.delete(prescriptionDetails);
                        System.out.println("hello");
                    }
                    List<PatientVitals> patientVitalsList = patientVitalsRepository.findByOpId(opId);
                    PatientVitals vitals = patientVitalsList.getFirst();
                    patientVitalsRepository.delete(vitals);
                    opConsultationRepository.delete(opConsultation);
                }
                appointmentRepository.delete(appointment);
            }
            patientDetailsRepository.delete(patientDetails);
            RecordDeletionLogDetails recordDeletionLogDetails = new RecordDeletionLogDetails();
            LocalDateTime currentDateTime = LocalDateTime.now();
            Date currentDate = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());
            recordDeletionLogDetails.setDeletedOn(currentDate);
            recordDeletionLogDetails.setDeletedByName(jsonNode.get("name").asText());
            recordDeletionLogDetails.setDeletedByRole(jsonNode.get("role").asText());
            recordDeletionLogDetails.setDeletedRecordId(String.valueOf(patientId));
            recordDeletionLogDetails.setDeletedRecordType("PATIENT_RECORD");
            recordDeletionLogDetailsRepository.save(recordDeletionLogDetails);
            return ResponseEntity.ok().body("Patient and their records deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("No patient found with id: " + patientId);
        }
    }

    public ResponseEntity<?> deleteAppointment(JsonNode jsonNode) {
        int appointmentId = jsonNode.get("appointmentId").asInt();
        Optional<AppointmentDetails> appointmentOptional = appointmentRepository.findById(appointmentId);
        if (appointmentOptional.isPresent()) {
            AppointmentDetails appointmentDetails = appointmentOptional.get();
            System.out.println("Appointment found: " + appointmentDetails);

            Optional<OpConsultation> opConsultationOptional = opConsultationRepository.findByAppointmentDetails(appointmentDetails);
            if (opConsultationOptional.isPresent()) {
                OpConsultation opConsultation = opConsultationOptional.get();
                Integer opConsultationId = opConsultation.getOp_id();
                System.out.println("OpConsultation found: " + opConsultation);

                List<PatientVitals> patientVitalsList = patientVitalsRepository.findByOpId(opConsultationId);
                if (!patientVitalsList.isEmpty()) {
                    PatientVitals vitals = patientVitalsList.get(0);
                    patientVitalsRepository.delete(vitals);
                    System.out.println("Deleted patientVitals: " + vitals);
                } else {
                    System.out.println("No patientVitals found for OpConsultationId: " + opConsultationId);
                }

                List<PrescriptionDetails> prescriptionDetailsList = prescriptionDetailsRepository.findByOpConsultation(opConsultationId);
                if (!prescriptionDetailsList.isEmpty()) {
                    prescriptionDetailsRepository.deleteAll(prescriptionDetailsList);
                    System.out.println("Deleted prescriptionDetailsList: " + prescriptionDetailsList);
                } else {
                    System.out.println("No prescriptionDetails found for OpConsultationId: " + opConsultationId);
                }

                opConsultationRepository.delete(opConsultation);
                System.out.println("Deleted opConsultation: " + opConsultation);
            } else {
                System.out.println("No OpConsultation found for AppointmentId: " + appointmentId);
            }

            appointmentRepository.delete(appointmentDetails);
            System.out.println("Deleted appointmentDetails: " + appointmentDetails);

            RecordDeletionLogDetails recordDeletionLogDetails = new RecordDeletionLogDetails();
            LocalDateTime currentDateTime = LocalDateTime.now();
            Date currentDate = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());
            recordDeletionLogDetails.setDeletedOn(currentDate);
            recordDeletionLogDetails.setDeletedByName(jsonNode.get("name").asText());
            recordDeletionLogDetails.setDeletedByRole(jsonNode.get("role").asText());
            recordDeletionLogDetails.setDeletedRecordId(String.valueOf(appointmentId));
            recordDeletionLogDetails.setDeletedRecordType("APPOINTMENT");
            recordDeletionLogDetailsRepository.save(recordDeletionLogDetails);
            return ResponseEntity.ok().body("Appointment deleted Successfully");
        } else {
            System.out.println("No appointment found for appointmentId: " + appointmentId);
            return ResponseEntity.badRequest().body("No appointment found");
        }
    }

    public List<PatientRegistrationLogDetails> getAllPatientRegistrationDetailsLogs() {
        return patientRegistrationLogDetailsRepository.findAll();
    }

    public List<RecordDeletionLogDetails> getAllRecordDeletionLogs() {
        return recordDeletionLogDetailsRepository.findAll();
    }
}


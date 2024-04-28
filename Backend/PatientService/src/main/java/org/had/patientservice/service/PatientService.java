package org.had.patientservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import org.had.accountservice.entity.DoctorDetails;
import org.had.accountservice.exception.MyWebClientException;
import org.had.patientservice.dto.PatientDetailsDto;
import org.had.patientservice.entity.*;
import org.had.patientservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
        if (patientDetailsRepository.existsByAbhaAddress(patientDetailsDto.getAbhaAddress())) {
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

    public ResponseEntity<?> deletePatient(Integer patientId) {
        if(patientDetailsRepository.findById(patientId).isPresent()) {
            PatientDetails patientDetails = patientDetailsRepository.findById(patientId).get();
            List<AppointmentDetails> appointmentDetails = appointmentRepository.findAllByPatientId(patientDetails);
            System.out.println("1");
            if(appointmentDetails != null ) {
                for(AppointmentDetails appointment : appointmentDetails) {
                    OpConsultation opConsultation = opConsultationRepository.findByAppointmentDetails(appointment).get();
                    Integer opConsulatationId = opConsultation.getOp_id();
                    System.out.println("2");
                    List<PatientVitals> patientVitalsList = patientVitalsRepository.findByOpId(opConsulatationId);
                    PatientVitals vitals = patientVitalsList.getFirst();
                    patientVitalsRepository.delete(vitals);
                    System.out.println("3");
                    List<PrescriptionDetails> prescriptionDetailsList = prescriptionDetailsRepository.findByOpConsultation(opConsulatationId);
                    if(prescriptionDetailsList != null) {
                        for(PrescriptionDetails prescriptionDetails : prescriptionDetailsList)
                            prescriptionDetailsRepository.delete(prescriptionDetails);
                        System.out.println("4");
                    }
                    appointmentRepository.delete(appointment);
                    opConsultationRepository.delete(opConsultation);
                }
            }
            patientDetailsRepository.delete(patientDetails);
        }
        else
            return ResponseEntity.badRequest().body("No patient found");
        return ResponseEntity.ok().body("Patient and their records deleted successfully");
    }


    public ResponseEntity<?> deleteAppointement(Integer appointmentId) {
        AppointmentDetails appointmentDetails = appointmentRepository.findById(appointmentId).get();
        if(appointmentDetails != null) {
            OpConsultation opConsultation = opConsultationRepository.findByAppointmentDetails(appointmentDetails).get();
            Integer opConsulatationId = opConsultation.getOp_id();
            List<PatientVitals> patientVitalsList = patientVitalsRepository.findByOpId(opConsulatationId);
            PatientVitals vitals = patientVitalsList.getFirst();
            patientVitalsRepository.delete(vitals);
            List<PrescriptionDetails> prescriptionDetailsList = prescriptionDetailsRepository.findByOpConsultation(opConsulatationId);
            if(prescriptionDetailsList != null) {
                for(PrescriptionDetails prescriptionDetails : prescriptionDetailsList)
                    prescriptionDetailsRepository.delete(prescriptionDetails);
            }
            appointmentRepository.delete(appointmentDetails);
            opConsultationRepository.delete(opConsultation);
        }
        else
            return ResponseEntity.badRequest().body("No appointment found");
        return ResponseEntity.ok().body("Appointment deleted Successfully");
    }
}


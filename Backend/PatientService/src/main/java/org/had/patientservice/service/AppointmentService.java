package org.had.patientservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.had.accountservice.exception.MyWebClientException;
import org.had.patientservice.dto.AppointmentDto;
import org.had.patientservice.entity.*;
import org.had.patientservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private OpConsultationRepository opConsultationRepository;

    @Autowired
    private PatientVitalsRepository patientVitalsRepository;
    
    @Autowired
    private PatientDetailsRepository patientDetailsRepository;

    @Autowired
    private PrescriptionDetailsRepository prescriptionDetailsRepository;
    @Autowired
    private WebClient webClient;

    public String createNewAppointment(AppointmentDto appointmentDto){
        AppointmentDetails appointmentDetails = new AppointmentDetails();
        appointmentDetails.setDoctor_id(appointmentDto.getDoctor_id());
        appointmentDetails.setDoctor_name(appointmentDto.getDoctor_name());

        PatientDetails patientDetails = patientDetailsRepository.findById(appointmentDto.getPatient_id())
                .orElseThrow(() -> new RuntimeException("Parent not found"));
        appointmentDetails.setPatientId(patientDetails);

        appointmentDetails.setDate(appointmentDto.getDate());
        appointmentDetails.setTime(appointmentDto.getTime());
        appointmentDetails.setNotes(appointmentDto.getNotes());

        AppointmentDetails appointmentId = appointmentRepository.save(appointmentDetails);

        OpConsultation opConsultation = new OpConsultation();
        opConsultation.setAppointmentDetails(appointmentId);

        OpConsultation opConsultationId = opConsultationRepository.save(opConsultation);

        addPatientVitals(opConsultationId, appointmentDto);

        return "successfully created appointment";
    }

    public String addPatientVitals(OpConsultation opConsultation, AppointmentDto appointmentDto){
        PatientVitals patientVitals = new PatientVitals();
        patientVitals.setWeight(appointmentDto.getWeight());
        patientVitals.setHeight(appointmentDto.getHeight());
        patientVitals.setAge(appointmentDto.getAge());
        patientVitals.setTemperature(appointmentDto.getTemperature());
        patientVitals.setBlood_pressure_systolic(appointmentDto.getBlood_pressure_systolic());
        patientVitals.setBlood_pressure_distolic(appointmentDto.getBlood_pressure_distolic());
        patientVitals.setPulse_rate(appointmentDto.getPulse_rate());
        patientVitals.setRespiration_rate(appointmentDto.getRespiration_rate());
        patientVitals.setBlood_sugar(appointmentDto.getBlood_sugar());
        patientVitals.setCholesterol(appointmentDto.getCholesterol());
        patientVitals.setTriglyceride(appointmentDto.getTriglyceride());

        patientVitals.setOpConsultation(opConsultation);

        patientVitalsRepository.save(patientVitals);

        return "successfully added vitals";
    }

    public ResponseEntity<?> getAppointmentDetails(Integer id){
        if(!patientDetailsRepository.findById(id).isEmpty()){
            PatientDetails patientDetails = patientDetailsRepository.findById(id).get();
            if(appointmentRepository.findAllByPatientId(patientDetails).isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Appointment found for this patient");
            }
            List<AppointmentDetails> appointmentDetails = appointmentRepository.findAllByPatientId(patientDetails);
            return ResponseEntity.ok(appointmentDetails);
        }
        return ResponseEntity.badRequest().body("Patient Not found");
    }


    public ResponseEntity<?> getPatientVitals(Integer opId) {
        List<PatientVitals> patientVitalsList = patientVitalsRepository.findByOpId(opId);
        if (!patientVitalsList.isEmpty()) {
            PatientVitals patientVitals = patientVitalsList.get(0); // Assuming you only want the first item in the list
            return ResponseEntity.ok(patientVitals);
        }
        return ResponseEntity.badRequest().body("Patient Vitals Not found");
    }


    public ResponseEntity<?> completeAppointment(JsonNode jsonNode) {
        try {
            JsonNode prescriptionArray = jsonNode.get("prescription");
            Integer opId = jsonNode.get("opId").asInt();
            String accessToken = jsonNode.get("accessToken").asText();
            Optional<OpConsultation> opConsultationOptional = opConsultationRepository.findById(opId);

            if (opConsultationOptional.isPresent()) {
                OpConsultation opConsultation = opConsultationOptional.get();

                if (prescriptionArray != null && prescriptionArray.isArray()) {
                    for (JsonNode prescriptionObject : prescriptionArray) {
                        String drug = prescriptionObject.get("drug").asText();
                        String instructions = prescriptionObject.get("instructions").asText();
                        Integer dosage = prescriptionObject.get("dosage").asInt();
                        Integer duration = prescriptionObject.get("duration").asInt();
                        Integer frequency = prescriptionObject.get("frequency").asInt();

                        PrescriptionDetails prescriptionDetails = new PrescriptionDetails();
                        prescriptionDetails.setDrug(drug);
                        prescriptionDetails.setInstructions(instructions);
                        prescriptionDetails.setDosage(dosage);
                        prescriptionDetails.setFrequency(frequency);
                        prescriptionDetails.setDuration(duration);
                        prescriptionDetails.setOpConsultation(opConsultation);

                        prescriptionDetailsRepository.save(prescriptionDetails);
                    }

                    linkCareContext(opId+"", accessToken);
                    return ResponseEntity.ok("Prescription Record saved");
                } else {
                    return ResponseEntity.badRequest().body("Invalid Prescription Array");
                }
            } else {
                return ResponseEntity.badRequest().body("OpConsultation with id " + opId + " not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    private String linkCareContext(String opId, String accessToken) {
        var values = new HashMap<String, String>() {{
            put("opId",opId);
            put("accessToken", accessToken);
        }};
        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri("http://127.0.0.1:9008/abdm/patient/linkCareContext")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }
}

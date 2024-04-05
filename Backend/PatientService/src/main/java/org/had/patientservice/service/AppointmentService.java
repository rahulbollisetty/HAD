package org.had.patientservice.service;

import org.had.patientservice.dto.AppointmentDto;
import org.had.patientservice.entity.AppointmentDetails;
import org.had.patientservice.entity.OpConsultation;
import org.had.patientservice.entity.PatientDetails;
import org.had.patientservice.entity.PatientVitals;
import org.had.patientservice.repository.AppointmentRepository;
import org.had.patientservice.repository.OpConsultationRepository;
import org.had.patientservice.repository.PatientDetailsRepository;
import org.had.patientservice.repository.PatientVitalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;
import java.util.List;

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
}

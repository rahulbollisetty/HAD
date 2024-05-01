package org.had.patientservice.repository;

import org.had.patientservice.entity.AppointmentDetails;
import org.had.patientservice.entity.PatientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<AppointmentDetails, Integer> {
    List<AppointmentDetails> findAllByPatientId(PatientDetails patientDetails);

    Optional<AppointmentDetails> findById(Integer appointmentId);

    List<AppointmentDetails> findByPatientIdAndDoctorName(PatientDetails patient, String doctorName);
}

package org.had.patientservice.repository;

import org.had.patientservice.entity.AppointmentDetails;
import org.had.patientservice.entity.PatientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentDetails, Integer> {
    List<AppointmentDetails> findAllByPatientId(PatientDetails patientDetails);
}

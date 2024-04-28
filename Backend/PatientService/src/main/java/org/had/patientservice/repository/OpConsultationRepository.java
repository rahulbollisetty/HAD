package org.had.patientservice.repository;

import org.had.patientservice.entity.AppointmentDetails;
import org.had.patientservice.entity.OpConsultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpConsultationRepository extends JpaRepository<OpConsultation, Integer> {
    Optional<OpConsultation> findById(Integer id);

    Optional<OpConsultation> findByAppointmentDetails(AppointmentDetails appointmentDetails);
}

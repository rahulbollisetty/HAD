package org.had.patientservice.repository;

import org.had.patientservice.entity.OpConsultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpConsultationRepository extends JpaRepository<OpConsultation, Integer> {
}

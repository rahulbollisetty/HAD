package org.had.patientservice.repository;

import org.had.patientservice.entity.OpConsultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpConsultationRepository extends JpaRepository<OpConsultation, Integer> {
    @Override
    Optional<OpConsultation> findById(Integer integer);
}

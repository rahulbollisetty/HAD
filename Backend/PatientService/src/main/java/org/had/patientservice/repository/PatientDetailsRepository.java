package org.had.patientservice.repository;

import org.had.patientservice.entity.PatientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientDetailsRepository extends JpaRepository<PatientDetails, Integer> {
    boolean existsByAbhaAddress(String abhaAddress);

    boolean existsByAbhaNumber(String abhaNumber);

    Optional<PatientDetails> findByAbhaAddress(String abhaAddress);
}

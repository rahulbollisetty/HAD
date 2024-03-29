package org.had.patientservice.repository;

import org.had.patientservice.entity.PatientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientDetailsRepository extends JpaRepository<PatientDetails, Integer> {
}

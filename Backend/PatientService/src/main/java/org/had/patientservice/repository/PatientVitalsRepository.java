package org.had.patientservice.repository;

import org.had.patientservice.entity.PatientVitals;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientVitalsRepository extends JpaRepository<PatientVitals, Integer> {
}

package org.had.patientservice.repository;

import org.had.patientservice.entity.PatientRegistrationLogDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRegistrationLogDetailsRepository extends JpaRepository<PatientRegistrationLogDetails, Long> {

}

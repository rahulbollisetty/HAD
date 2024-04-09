package org.had.patientservice.repository;

import org.had.patientservice.entity.PrescriptionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionDetailsRepository extends JpaRepository<PrescriptionDetails, Integer> {
}

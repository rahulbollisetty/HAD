package org.had.patientservice.repository;

import org.had.patientservice.entity.RecordDeletionLogDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordDeletionLogDetailsRepository extends JpaRepository<RecordDeletionLogDetails, Long> {
}

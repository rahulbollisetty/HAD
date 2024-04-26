package org.had.patientservice.repository;

import ch.qos.logback.core.model.INamedModel;
import org.had.patientservice.entity.ConsentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsentDetailRepository extends JpaRepository<ConsentDetails, Integer> {

    Optional<ConsentDetails> findByConsentId(String consentId);
}

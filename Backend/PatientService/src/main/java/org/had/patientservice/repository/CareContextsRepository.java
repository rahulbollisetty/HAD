package org.had.patientservice.repository;

import org.had.patientservice.entity.CareContexts;
import org.had.patientservice.entity.ConsentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CareContextsRepository extends JpaRepository<CareContexts,Integer> {

    Set<CareContexts> findByConsentDetails(ConsentDetails consentDetails);
}

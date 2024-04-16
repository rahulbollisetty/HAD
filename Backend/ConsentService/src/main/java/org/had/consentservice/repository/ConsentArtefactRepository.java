package org.had.consentservice.repository;

import org.had.consentservice.entity.ConsentArtefact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConsentArtefactRepository extends JpaRepository<ConsentArtefact, Long> {
    
    @Query("SELECT ca FROM ConsentArtefact ca JOIN ca.consentRequest cr WHERE cr.consent_id = :consentId")
    Optional<ConsentArtefact> findAllByConsentRequest_ConsentId(String consentId);

    @Query("SELECT ca FROM ConsentArtefact ca WHERE ca.consent_artefact = :consentArtefactId")
    Optional<ConsentArtefact> findConsentArtefactBy(String consentArtefactId);
}
package org.had.consentservice.repository;

import ch.qos.logback.core.model.INamedModel;
import org.had.consentservice.entity.ConsentArtefact;
import org.had.consentservice.entity.ConsentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConsentArtefactRepository extends JpaRepository<ConsentArtefact, Long> {
    
    @Query("SELECT ca FROM ConsentArtefact ca JOIN ca.consentRequest cr WHERE cr.consent_id = :consentId")
    Optional<ConsentArtefact> findAllByConsentRequest_ConsentId(String consentId);

    Optional<ConsentArtefact> findByConsentArtefact(String consentArtefactId);


@Query("SELECT COUNT(ca) > 0 FROM ConsentArtefact ca " +
        "JOIN ca.careContexts cc " +
        "WHERE ca.consentRequest.id = :consentId " +
        "AND cc.hipId = :hipId " +
        "AND cc.careContextReference = :careContextReference ")
boolean existsByConsentIdAndCareContexts(Integer consentId,
                                         String careContextReference,
                                         String hipId);

    List<ConsentArtefact> findAllByConsentRequest(ConsentRequest consentRequest);
}
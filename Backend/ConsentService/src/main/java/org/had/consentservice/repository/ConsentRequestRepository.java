package org.had.consentservice.repository;

import org.had.consentservice.entity.ConsentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConsentRequestRepository extends JpaRepository<ConsentRequest, Long> {
//    Optional<ConsentRequest> findByRequest_id(String requestId);
//
//    Optional<ConsentRequest> findByConsent_id(String consentId);


    @Query("SELECT cr FROM ConsentRequest cr WHERE cr.request_id = :requestId")
    Optional<ConsentRequest> findByRequest_id(String requestId);

    @Query("SELECT cr FROM ConsentRequest cr WHERE cr.consent_id = :consent_id")
    Optional<ConsentRequest> findByConsent_id(String consent_id);

    @Query("SELECT cr from ConsentRequest cr WHERE cr.patient_id = :patientId AND cr.identifier_value = :docRegNo AND cr.consent_id IS NOT NULL")
    List<ConsentRequest> findByDocRegNoAndPatientId(String docRegNo, Integer patientId);

}

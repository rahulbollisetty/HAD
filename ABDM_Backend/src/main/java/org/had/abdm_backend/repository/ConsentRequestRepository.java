package org.had.abdm_backend.repository;

import org.had.abdm_backend.entity.ConsentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConsentRequestRepository extends JpaRepository<ConsentRequest, Long> {

    @Query("SELECT cr FROM ConsentRequest cr WHERE cr.request_id = :requestId")
    Optional<ConsentRequest> findByRequest_id(String requestId);

}

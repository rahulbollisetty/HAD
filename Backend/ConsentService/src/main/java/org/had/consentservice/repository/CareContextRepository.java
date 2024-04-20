package org.had.consentservice.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.had.consentservice.entity.CareContexts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareContextRepository extends JpaRepository<CareContexts, Integer> {
}

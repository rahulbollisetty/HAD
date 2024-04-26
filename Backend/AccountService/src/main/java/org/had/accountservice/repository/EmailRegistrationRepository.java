package org.had.accountservice.repository;

import org.had.accountservice.entity.EmailRegistrationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRegistrationRepository extends JpaRepository<EmailRegistrationDetails, Integer> {
    EmailRegistrationDetails findByToken(String token);
}

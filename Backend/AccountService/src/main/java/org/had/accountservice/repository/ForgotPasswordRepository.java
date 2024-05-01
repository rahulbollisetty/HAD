package org.had.accountservice.repository;


import org.had.accountservice.entity.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, String> {
    Optional<ForgotPassword> findByEmail(String email);

    Optional<ForgotPassword> findByToken(String token);
}

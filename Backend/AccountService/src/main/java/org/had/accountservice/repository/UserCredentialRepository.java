package org.had.accountservice.repository;

import org.had.accountservice.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential,Integer> {
    Optional<UserCredential> findByUsername(String username);
}

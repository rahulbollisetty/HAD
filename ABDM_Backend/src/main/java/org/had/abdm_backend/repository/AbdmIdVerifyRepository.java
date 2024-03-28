package org.had.abdm_backend.repository;

import org.had.abdm_backend.entity.AbdmIdVerify;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AbdmIdVerifyRepository extends JpaRepository<AbdmIdVerify,Integer> {

    Optional<AbdmIdVerify> findByInitRequestId(String requestId);
}
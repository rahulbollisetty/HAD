package org.had.accountservice.repository;

import org.had.accountservice.entity.DoctorDetails;
import org.had.accountservice.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorDetailsRepository extends JpaRepository<DoctorDetails, Integer> {

    Optional<DoctorDetails> findByLoginCredential(UserCredential user);
}

package org.had.accountservice.repository;

import org.had.accountservice.entity.DoctorDetails;
import org.had.accountservice.entity.StaffDetails;
import org.had.accountservice.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffDetailsRepository extends JpaRepository<StaffDetails, Integer> {

    Optional<StaffDetails> findByLoginCredential(UserCredential user);

//    Optional<StaffDetails> findByUsername(String username);
}

package org.had.accountservice.repository;

import org.had.accountservice.entity.DoctorDetails;
import org.had.accountservice.entity.StaffDetails;
import org.had.accountservice.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorDetailsRepository extends JpaRepository<DoctorDetails, Integer> {

    Optional<DoctorDetails> findByLoginCredential(UserCredential user);

    Optional<DoctorDetails> findById(Integer id);

}

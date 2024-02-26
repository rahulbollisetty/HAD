package org.had.accountservice.repository;

import org.had.accountservice.entity.DoctorDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorDetailsRepository extends JpaRepository<DoctorDetails, Integer> {
}

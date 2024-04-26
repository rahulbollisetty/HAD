package org.had.accountservice.repository;

import org.had.accountservice.entity.HospitalDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalDetailsRepository extends JpaRepository<HospitalDetails, Integer> {
}

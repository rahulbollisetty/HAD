package org.had.accountservice.repository;

import org.had.accountservice.entity.StaffDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffDetailsRepository extends JpaRepository<StaffDetails, Integer> {

}
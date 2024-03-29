package org.had.patientservice.repository;

import org.had.patientservice.entity.AppointmentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<AppointmentDetails, Integer> {

}

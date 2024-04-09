package org.had.patientservice.repository;

import org.had.patientservice.entity.PatientVitals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientVitalsRepository extends JpaRepository<PatientVitals, Integer> {

    @Query("SELECT pv FROM PatientVitals pv WHERE pv.opConsultation.op_id = :opId")
    List<PatientVitals> findByOpId(Integer opId);
}

package org.had.patientservice.repository;

import org.had.patientservice.entity.OpConsultation;
import org.had.patientservice.entity.PrescriptionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PrescriptionDetailsRepository extends JpaRepository<PrescriptionDetails, Integer> {
    @Query("SELECT pv FROM PrescriptionDetails pv WHERE pv.opConsultation.op_id = :opId")
    List<PrescriptionDetails> findByOpConsultation(@Param("opId") Integer opId);
}


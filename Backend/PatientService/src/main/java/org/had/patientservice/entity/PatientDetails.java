package org.had.patientservice.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class PatientDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String mrn;

    @Column(nullable = false)
    private String full_name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String abha_id;

    private Integer year_of_birth;

    private String mobile_number;

    private String gender;

    private String hospital_id;


}

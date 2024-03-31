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
    private String abhaNumber;

    @Column(nullable = false)
    private String abhaAddress;

    private String year_of_birth;

    private String gender;

    private String mobileNumber;

    private String email;

    private String DOB;

    private String bloodGroup;

    private String occupation;

    private String familyMemberName;

    private String relationship;

    private String town;

    private String pincode;

    private String state;

    private String hospital_id;


}

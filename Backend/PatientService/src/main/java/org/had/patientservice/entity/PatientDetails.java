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
    private Integer mrn;

    private String name;

    private String address;

    private String abhaAddress;

    private String abhaNumber;

    private String gender;

    private String mobileNumber;

    private String email;

    private String dob;

    private String bloodGroup;

    private String occupation;

    private String familyMemberName;

    private String relationship;

    private String town;

    private String pincode;

    private String state;

    @Column(length = 3000)
    private String linkToken;

//    private String hospital_id;

}

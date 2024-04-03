package org.had.patientservice.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class PatientDetailsDto {
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
}

package org.had.patientservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PatientRegistrationLogDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String generatedByName;

    @NotNull
    private String role;

    @NotNull
    private String registrationMethod = "AADHAAR_OTP";

    @NotNull
    private String patientName;

    @NotNull
    private Date generatedAt;
}

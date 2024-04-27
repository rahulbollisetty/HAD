package org.had.accountservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Doctor_Id;

    @NotNull
    private String hpr_Id;

    @NotNull
    private String registration_number;

    @NotNull
    private String first_Name;

    @NotNull
    private String last_Name;

    @NotNull
    private String dob;

    @NotNull
    private String gender;

    @NotNull
    private Integer state_Code;

    @NotNull
    private String state;

    @NotNull
    private String mobile;

    @NotNull
    private Integer district_Code;

    @NotNull
    private String district;

    @NotNull
    private Integer pincode;

    @NotNull
    private Boolean isHeadDoctor;

    @NotNull
    private String address;

    @JsonIgnore
    private final LocalDateTime createdAt = LocalDateTime.now();

    @JsonIgnore
    private final LocalDateTime editedAt = LocalDateTime.now();

    @OneToOne(targetEntity = UserCredential.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "login_id", referencedColumnName = "UserCred_id", unique = true)
    private UserCredential loginCredential;
}

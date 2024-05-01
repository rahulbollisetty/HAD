package org.had.accountservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DoctorDetailsDTO {

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
    private Integer district_Code;

    @NotNull
    private String district;

    @NotNull
    private Integer pincode;

    @NotNull
    private String mobile;

    @NotNull
    private String address;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private Boolean isHeadDoctor;

    @NotNull
    private String email;

}


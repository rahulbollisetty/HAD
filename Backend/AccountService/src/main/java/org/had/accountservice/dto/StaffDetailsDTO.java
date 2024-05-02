package org.had.accountservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffDetailsDTO {
    @NotNull
    private String first_Name;

    @NotNull
    private  String last_Name;

    @NotNull
    private String dob;

    @NotNull
    private String gender;

    @NotNull
    private String state;

    @NotNull
    private String district;

    @NotNull
    private String mobile;

    @NotNull
    private String pincode;

    @NotNull
    private String address;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;
}

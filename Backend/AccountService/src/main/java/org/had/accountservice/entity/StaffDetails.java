package org.had.accountservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StaffDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Staff_Id;

    @NotNull
    private String first_Name;

    @NotNull
    private  String last_Name;

    @NotNull
    private Date dob;

    @NotNull
    private String gender;

    private String mobile;

    private String state;

    @NotNull
    private String district;

    private String pincode;

    @NotNull
    private String address;


    @OneToOne(targetEntity = UserCredential.class, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "login_id", referencedColumnName = "UserCred_id", unique = true)
    private UserCredential loginCredential;
}

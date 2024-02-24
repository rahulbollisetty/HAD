package org.had.accountservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

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
    private Integer hpr_Id;

    @NotNull
    private String First_Name;

    @NotNull
    private  String Last_Name;

    @NotNull
    private Date DOB;

    @NotNull
    private String Gender;

    @NotNull
    private String State;

    @NotNull
    private String District;

    @NotNull
    private String Town;

    @NotNull
    private String Street;

    @NotNull
    private String Username;

    @NotNull
    private boolean isHeadDoctor;

    @OneToOne(targetEntity = UserCredential.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "login_id", referencedColumnName = "UserCred_id", unique = true)
    private UserCredential loginCredential;
}

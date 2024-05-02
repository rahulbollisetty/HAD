package org.had.accountservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.had.accountservice.converters.StringCryptoConverter;

import java.sql.Date;


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
    @Convert(converter = StringCryptoConverter.class)
    private String first_Name;

    @NotNull
    @Convert(converter = StringCryptoConverter.class)
    private  String last_Name;

    @NotNull
    @Convert(converter = StringCryptoConverter.class)
    private String dob;

    @NotNull
    @Convert(converter = StringCryptoConverter.class)
    private String gender;

    @NotNull
    @Convert(converter = StringCryptoConverter.class)
    private String mobile;

    @NotNull
    @Convert(converter = StringCryptoConverter.class)
    private String state;

    @NotNull
    @Convert(converter = StringCryptoConverter.class)
    private String email;

    @NotNull
    @Convert(converter = StringCryptoConverter.class)
    private String district;

    @Convert(converter = StringCryptoConverter.class)
    private String pincode;

    @NotNull
    @Convert(converter = StringCryptoConverter.class)
    private String address;


    @OneToOne(targetEntity = UserCredential.class, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "login_id", referencedColumnName = "UserCred_id", unique = true)
    private UserCredential loginCredential;
}

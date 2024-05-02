package org.had.accountservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.had.accountservice.converters.AttributeEncryptor;
import org.had.accountservice.converters.IntegerCryptoConverter;
import org.had.accountservice.converters.StringCryptoConverter;

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
    @Convert(converter = StringCryptoConverter.class)
    private String hpr_Id;

    @NotNull
    @Convert(converter = StringCryptoConverter.class)
    private String registration_number;

    @NotNull
    @Convert(converter = StringCryptoConverter.class)
    private String first_Name;

    @NotNull
    @Convert(converter = StringCryptoConverter.class)
    private String last_Name;

    @NotNull
    @Convert(converter = StringCryptoConverter.class)
    private String email;

    @NotNull
    @Convert(converter = StringCryptoConverter.class)
    private String dob;

    @NotNull
    @Convert(converter = StringCryptoConverter.class)
    private String gender;

    @NotNull
    @Convert(converter = IntegerCryptoConverter.class)
    private Integer state_Code;

    @NotNull
    @Convert(converter = StringCryptoConverter.class)
    private String state;

    @NotNull
    @Convert(converter = StringCryptoConverter.class)
    private String mobile;

    @NotNull
    @Convert(converter = IntegerCryptoConverter.class)
    private Integer district_Code;

    @NotNull
    @Convert(converter = StringCryptoConverter.class)
    private String district;

    @NotNull
    @Convert(converter = IntegerCryptoConverter.class)
    private Integer pincode;

    @NotNull
    private Boolean isHeadDoctor;

    @NotNull
    @Convert(converter = StringCryptoConverter.class)
    private String address;

    @JsonIgnore
    private final LocalDateTime createdAt = LocalDateTime.now();

    @JsonIgnore
    private final LocalDateTime editedAt = LocalDateTime.now();

    @OneToOne(targetEntity = UserCredential.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "login_id", referencedColumnName = "UserCred_id", unique = true)
    @JsonIgnore
    private UserCredential loginCredential;
}

package org.had.patientservice.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.had.patientservice.converters.StringCryptoConverter;

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

    @Convert(converter = StringCryptoConverter.class)
    private String name;

    @Convert(converter = StringCryptoConverter.class)
    private String address;

    @Convert(converter = StringCryptoConverter.class)
    private String abhaAddress;

    @Convert(converter = StringCryptoConverter.class)
    private String abhaNumber;

    @Convert(converter = StringCryptoConverter.class)
    private String gender;

    @Convert(converter = StringCryptoConverter.class)
    private String mobileNumber;

    @Convert(converter = StringCryptoConverter.class)
    private String email;

    @Convert(converter = StringCryptoConverter.class)
    private String dob;

    @Convert(converter = StringCryptoConverter.class)
    private String bloodGroup;

    @Convert(converter = StringCryptoConverter.class)
    private String occupation;

    @Convert(converter = StringCryptoConverter.class)
    private String familyMemberName;

    @Convert(converter = StringCryptoConverter.class)
    private String relationship;

    @Convert(converter = StringCryptoConverter.class)
    private String town;

    @Convert(converter = StringCryptoConverter.class)
    private String pincode;

    @Convert(converter = StringCryptoConverter.class)
    private String state;

    @Column(length = 3000)
    @JsonIgnore
    private String linkToken;

//    private String hospital_id;

}

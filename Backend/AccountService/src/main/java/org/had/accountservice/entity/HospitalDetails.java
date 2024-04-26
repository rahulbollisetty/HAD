package org.had.accountservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HospitalDetails {

    @Id
    private String hospital_id;

    @NotNull
    private String hospital_name;

    @NotNull
    private Integer state_code;

    @NotNull
    private String state;

    @NotNull
    private Integer district_code;

    @NotNull
    private String district;

    @NotNull
    private String address;

    @NotNull
    private Integer pincode;

    @NotNull
    private String specialization;
}

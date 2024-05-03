package org.had.patientservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.had.patientservice.converters.IntegerCryptoConverter;
import org.had.patientservice.converters.StringCryptoConverter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PrescriptionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer prescriptionId;

    @Convert(converter = StringCryptoConverter.class)
    private String drug;

    @Convert(converter = StringCryptoConverter.class)
    private String instructions;

    @Convert(converter = IntegerCryptoConverter.class)
    private Integer dosage;

    @Convert(converter = IntegerCryptoConverter.class)
    private Integer frequency;

    @Convert(converter = IntegerCryptoConverter.class)
    private Integer duration;

    @NotNull
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "op_id")
    private OpConsultation opConsultation;
}

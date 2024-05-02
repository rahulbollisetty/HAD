package org.had.patientservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.had.accountservice.converters.StringCryptoConverter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class OpConsultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer op_id;

    @Convert(converter = StringCryptoConverter.class)
    private String observations;

    @Convert(converter = StringCryptoConverter.class)
    private String filePath;

    @Convert(converter = StringCryptoConverter.class)
    private String fileDescription;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private AppointmentDetails appointmentDetails;
}

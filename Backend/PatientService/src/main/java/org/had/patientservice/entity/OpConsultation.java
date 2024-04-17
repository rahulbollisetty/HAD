package org.had.patientservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    private String observations;

    private String filePath;

    private String fileDescription;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private AppointmentDetails appointmentDetails;
}

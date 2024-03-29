package org.had.patientservice.entity;

import jakarta.persistence.*;
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

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private AppointmentDetails appointmentDetails;
}

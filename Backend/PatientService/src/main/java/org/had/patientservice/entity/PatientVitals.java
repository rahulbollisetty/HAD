package org.had.patientservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class PatientVitals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vitals_id;

    @NotNull
    private Integer weight;

    @NotNull
    private Integer height;

    @NotNull
    private Integer age;

    @NotNull
    private Integer temperature;

    @NotNull
    private Integer blood_pressure_systolic;

    @NotNull
    private Integer blood_pressure_distolic;

    @NotNull
    private Integer pulse_rate;

    @NotNull
    private Integer respiration_rate;

    @NotNull
    private Integer blood_sugar;

    @NotNull
    private Integer cholesterol;

    @NotNull
    private Integer triglyceride;

    @NotNull
    @OneToOne
    @JoinColumn(name = "op_id")
    private OpConsultation opConsultation;
}

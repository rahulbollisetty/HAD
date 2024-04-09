package org.had.patientservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private Integer weight;

    private Integer height;

    private Integer age;

    private Integer temperature;

    private Integer blood_pressure_systolic;

    private Integer blood_pressure_distolic;

    private Integer pulse_rate;

    private Integer respiration_rate;

    private Integer blood_sugar;

    private Integer cholesterol;

    private Integer triglyceride;

    @NotNull
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "op_id")
    private OpConsultation opConsultation;


}

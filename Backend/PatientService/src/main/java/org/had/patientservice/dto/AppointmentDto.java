package org.had.patientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {

    private String doctor_id;

    private Integer patient_id;

    private String date;

    private String time;

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
}

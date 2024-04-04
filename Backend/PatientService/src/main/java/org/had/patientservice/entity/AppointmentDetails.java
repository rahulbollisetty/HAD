package org.had.patientservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class AppointmentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appointment_id;

//    @NotNull
//    private Integer hospital_id;

    private String doctor_id;

    private Integer patient_id;

    private String date;

    private String time;

    @Column(columnDefinition = "varchar(25) default 'Upcoming' ")
    private String status;


}

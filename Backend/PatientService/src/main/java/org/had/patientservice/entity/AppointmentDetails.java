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

    private String doctor_id;

    private String doctorRegNumber;

    private String doctor_name;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "patientId", referencedColumnName = "mrn")
    private PatientDetails patientId;

    private String date;

    private String time;

    private String notes;

    @Column(length = 25)
    private String status = "Upcoming";

}

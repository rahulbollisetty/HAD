package org.had.patientservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecordDeletionLogDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String deletedByName;

    @NotNull
    private String deletedByRole;

    @NotNull
    private String deletedRecordId;

    @NotNull
    private String deletedRecordType;

    @NotNull
    private Date deletedOn;

    @NotNull
    private String recordDeletionRequestedBy = "PATIENT";
}

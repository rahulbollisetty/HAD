package org.had.consentservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CareContexts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String hip_name;

    private String patient_sbx;
//    try getting patient mrn

    private String hip_id;

    private String hi_types;

    private String purpose;

    private String requester_name;

    private String requester_identifier;

    private String initiated_at;

    private String data_erase_at;

    private String access_mode;

    private String patient_reference;

    private String care_context_reference;

    private String signature;

    private String content;

    @ManyToOne
    @JoinColumn(name = "consent_artefact")
    private ConsentArtefact consentArtefact;
}

package org.had.consentservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConsentArtefact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String consent_artefact;

    @ManyToOne
    @JoinColumn(name = "consent_id")
    private ConsentRequest consentRequest;
}

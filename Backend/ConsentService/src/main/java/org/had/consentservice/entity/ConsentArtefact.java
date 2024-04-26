package org.had.consentservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsentArtefact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String consentArtefact;

    @ManyToOne
    @JoinColumn(name = "consent_id")
    private ConsentRequest consentRequest;

    private String requesterPrivateKey;

    private String requesterNonce;

    private String transactionId;

    @Column(unique = true)
    private String heathInfoRequestId;

    private String healthInfoRequestStatus;

    @ManyToMany(cascade = {CascadeType.REMOVE ,CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "consent_artefact_context",
            joinColumns = @JoinColumn(name = "consent_artefact_id"),
            inverseJoinColumns = @JoinColumn(name = "context_id")
    )
    private Set<CareContexts> careContexts = new HashSet<>();

}

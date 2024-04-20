package org.had.patientservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String consentId;

    private String patientId;

    private String purposeText;

    private String purposeCode;

    private String consentStatus;

    private String hiTypes;

    private String permissionFrom;

    private String permissionTo;

    private String dataEraseAt;

    @ManyToMany(cascade = {CascadeType.REMOVE ,CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "consent_details_context",
            joinColumns = @JoinColumn(name = "consent_details_id"),
            inverseJoinColumns = @JoinColumn(name = "care_context_id")
    )
    private Set<CareContexts> careContexts = new HashSet<>();
}

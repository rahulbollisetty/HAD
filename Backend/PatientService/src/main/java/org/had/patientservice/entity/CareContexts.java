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
public class CareContexts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String patientSbx;

    private String patientReference;

    private String careContextReference;

    @ManyToMany(mappedBy = "careContexts")
    private Set<ConsentDetails> consentArtefacts = new HashSet<>();
}

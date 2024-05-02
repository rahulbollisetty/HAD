package org.had.consentservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.had.consentservice.converters.StringCryptoConverter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CareContexts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String hipId;

    private String hipName;

    @Convert(converter = StringCryptoConverter.class)
    private String patientSbx;

    private String patientReference;

    private String careContextReference;

    @Column(length = 500)
    private String signature;

    private String content;

    @ManyToMany(mappedBy = "careContexts")
    private Set<ConsentArtefact> consentArtefacts = new HashSet<>();

}

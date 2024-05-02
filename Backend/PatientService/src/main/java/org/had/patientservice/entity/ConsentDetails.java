package org.had.patientservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.had.accountservice.converters.StringCryptoConverter;

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

    @Convert(converter = StringCryptoConverter.class)
    private String status;

    @Convert(converter = StringCryptoConverter.class)
    private String patientId;

    @Convert(converter = StringCryptoConverter.class)
    private String purposeText;

    @Convert(converter = StringCryptoConverter.class)
    private String purposeCode;

    @Convert(converter = StringCryptoConverter.class)
    private String hiTypes;

    @Convert(converter = StringCryptoConverter.class)
    private String permissionFrom;

    @Convert(converter = StringCryptoConverter.class)
    private String permissionTo;

    @Convert(converter = StringCryptoConverter.class)
    private String dataEraseAt;

    @ManyToMany(cascade = {CascadeType.REMOVE ,CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "consent_details_context",
            joinColumns = @JoinColumn(name = "consent_details_id"),
            inverseJoinColumns = @JoinColumn(name = "care_context_id")
    )
    private Set<CareContexts> careContexts = new HashSet<>();
}

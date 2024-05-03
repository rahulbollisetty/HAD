package org.had.consentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.had.consentservice.converters.IntegerCryptoConverter;
import org.had.consentservice.converters.StringCryptoConverter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String consent_id;

    private String status = "REQUESTED";

    @Column(nullable = false, unique = true)
    private String request_id;

    @Convert(converter = StringCryptoConverter.class)
    private String patient_id_sbx;

    @Convert(converter = IntegerCryptoConverter.class)
    private Integer patient_id;

    @Convert(converter = StringCryptoConverter.class)
    private String requester_name;

    @Convert(converter = StringCryptoConverter.class)
    private String identifier_value;

    private String permission_from;

    private String permission_to;

    private String data_erase_at;

    @Convert(converter = StringCryptoConverter.class)
    private String purpose_code;

    @Convert(converter = StringCryptoConverter.class)
    private String hi_type;
}

package org.had.consentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String patient_id_sbx;

    private Integer patient_id;

    private String requester_name;

    private String identifier_value;

    private String permission_from;

    private String permission_to;

    private String data_erase_at;

    private String purpose_code;

    private String hi_type;
}

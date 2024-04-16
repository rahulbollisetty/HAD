package org.had.consentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConsentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String consent_id;

    private String status = "REQUESTED";

    @Column(nullable = false, unique = false)
    private String request_id;

    private String patient_id;

    private String requester_name;

    private String identifier_value;

    private String permission_from;

    private String permission_to;

    private String data_erase_at;

    private String purpose_code;

    private String hi_type;
}

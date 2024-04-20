package org.had.abdm_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String consentId;

    @Column(nullable = false, unique = true)
    private String request_id;

    private String status = "REQUESTED";

    private String routing_key;
}

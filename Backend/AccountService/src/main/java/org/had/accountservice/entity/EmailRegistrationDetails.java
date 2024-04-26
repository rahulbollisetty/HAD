package org.had.accountservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailRegistrationDetails {

    private static final int expiryTime = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private String role;

    private Date expiration;

    public EmailRegistrationDetails(String uuid, String role) {
        this.token = uuid;
        this.role = role;
        this.expiration = calculateExpiry(expiryTime);
    }

    public EmailRegistrationDetails(String uuid) {
        super();
        this.token = uuid;
        this.expiration = calculateExpiry(expiryTime);
    }

    private Date calculateExpiry(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, minutes);
        return new Date(calendar.getTime().getTime());
    }
}

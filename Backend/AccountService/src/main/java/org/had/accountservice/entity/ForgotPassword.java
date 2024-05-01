package org.had.accountservice.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class ForgotPassword {
    private static final int expiryTime = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String email;

    private String token;

    private String role;

    private Date expiration;

    private Integer facultyId;

    public ForgotPassword(String token, String role, String email, Integer id) {
        this.token = token;
        this.role = role;
        this.email = email;
        this.facultyId = id;
        this.expiration = calculateExpiry(expiryTime);
    }

    public ForgotPassword(String token) {
        super();
        this.token = token;
        this.expiration = calculateExpiry(expiryTime);
    }

    private Date calculateExpiry(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, minutes);
        return new Date(calendar.getTime().getTime());
    }
}

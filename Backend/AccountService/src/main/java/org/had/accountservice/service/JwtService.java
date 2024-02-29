package org.had.accountservice.service;

import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    public static final String SECRET = "71430565e99c3c6e3b37526f3c8bf1004582589a3fe7fd35eb2305e03677ea8c";


    public void validateToken(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) getSignKey()).build().parseSignedClaims(token);
        }
        catch (Exception e){
            throw e;
        }
    }

    public String generateToken(String username,String role){
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,username,role);
    }

    private String createToken(Map<String, Object> claims, String username,String role) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .claim("role",role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getSignKey()).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

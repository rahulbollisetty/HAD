package org.had.consentservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Collections;

@Service
public class JwtService {

    public static final String SECRET = "71430565e99c3c6e3b37526f3c8bf1004582589a3fe7fd35eb2305e03677ea8c";


    public Boolean validateToken(String token) {
        try{
            Jwts.parser().verifyWith((SecretKey) getSignKey()).build().parseSignedClaims(token).getPayload();
            return true;
        }
        catch (Exception e){
            System.out.println("Validate token failed");
            return false;
        }
    }

    public User getUserPrincipal(String token) {
        Claims claims = extractAllClaims(token);
        String username = claims.getSubject();
        String role = (String) claims.get("role");
        return new User(username, "", Collections.singleton(new SimpleGrantedAuthority(role)));
    }
    public String extractUsername(String token){
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    private Claims extractAllClaims(String token) {

        return Jwts
                .parser()
                .verifyWith((SecretKey) getSignKey())
                .build()
                .parseSignedClaims(token)
                .getBody();
    }
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

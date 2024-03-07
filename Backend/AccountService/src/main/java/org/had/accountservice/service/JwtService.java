package org.had.accountservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.had.accountservice.entity.UserCredential;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.security.Key;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    public static final String SECRET = "71430565e99c3c6e3b37526f3c8bf1004582589a3fe7fd35eb2305e03677ea8c";

//    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
//        String jwt = generateTokenFromUsername(userPrincipal.getUsername());
//        return generateCookie(jwtCookie, jwt, "/api");
//    }
//
//    public ResponseCookie generateJwtCookie(UserCredential user) {
//        String jwt = generateToken(user.getUsername(),user.getRole());
//        return generateCookie("", jwt, "/api");
//    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) getSignKey())
                .build()
                .parseSignedClaims(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public ResponseCookie generateRefreshJwtCookie(String refreshToken) {
        return generateCookie("JWT-refresh", refreshToken, "/");
    }

    public String getJwtRefreshFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request);
    }

    public ResponseCookie getCleanJwtRefreshCookie() {
        return ResponseCookie.from("JWT-refresh", null).path("/").build();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
            final String username = extractUsername(token);
            return  (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
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
                .expiration(new Date(System.currentTimeMillis()+1000*30))
                .signWith(getSignKey()).compact();
    }


    private String getCookieValueByName(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "JWT-refresh");
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    private ResponseCookie generateCookie(String name, String value, String path) {
        return ResponseCookie.from(name, value).path(path).secure(false).sameSite("None").secure(true).maxAge(24 * 60 * 60).httpOnly(true).build();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

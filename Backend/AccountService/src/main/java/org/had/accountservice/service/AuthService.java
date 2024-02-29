package org.had.accountservice.service;

import org.had.accountservice.entity.DoctorDetails;
import org.had.accountservice.entity.UserCredential;
import org.had.accountservice.repository.DoctorDetailsRepository;
import org.had.accountservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    private UserCredentialRepository repository;

    @Autowired
    private DoctorDetailsRepository doctorDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;


    public String generateToken(String username, String role) {
        return jwtService.generateToken(username,role);
    }

    public ResponseEntity<?> validateToken(String token){
        try {
            jwtService.validateToken(token);
            return ResponseEntity.ok("Token valid");
        }
        catch (Exception e){
            return new ResponseEntity<>("tokenInvalid", HttpStatus.BAD_REQUEST);
        }
    }

    public String getAuthoritiesAsString(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(""));
    }
}

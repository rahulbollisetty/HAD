package org.had.accountservice.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.had.accountservice.config.UserCredentialUserDetails;
import org.had.accountservice.dto.AuthRequest;
import org.had.accountservice.dto.DoctorDetailsDTO;
import org.had.accountservice.dto.DoctorHPR;
import org.had.accountservice.dto.StaffDetailsDTO;
import org.had.accountservice.service.AuthService;
import org.had.accountservice.service.DoctorService;
import org.had.accountservice.service.JwtService;
import org.had.accountservice.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private StaffService staffService;

    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(authenticate.isAuthenticated()){
            UserCredentialUserDetails credential = (UserCredentialUserDetails) authenticate.getPrincipal();
            String role = authService.getAuthoritiesAsString(credential.getAuthorities());
            return authService.generateToken(authRequest.getUsername(),role);
        }
        else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @PostMapping(value = "/registerDoctor" ,produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> registerDoctor(@Valid @RequestBody DoctorDetailsDTO doctorDetailsDTO) {
        String result = doctorService.addDoctor(doctorDetailsDTO);
        if(result.equals("Doctor added to system")){
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(doctorDetailsDTO.getUsername(), doctorDetailsDTO.getPassword()));
            if(authenticate.isAuthenticated()){
                UserCredentialUserDetails credential = (UserCredentialUserDetails) authenticate.getPrincipal();
                String role = authService.getAuthoritiesAsString(credential.getAuthorities());
                String token = authService.generateToken(doctorDetailsDTO.getUsername(),role);
                Map<String, String> response = new HashMap<>();
                response.put("Status", result);
                response.put("token", token);
                return ResponseEntity.ok(response);
            }
            else {
                throw new UsernameNotFoundException("invalid user request !");
            }
        }
        return  ResponseEntity.badRequest().body(result);
    }

    @PostMapping(value = "/registerStaff" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerStaff(@Valid @RequestBody StaffDetailsDTO staffDetailsDTO) {
        String result = staffService.addStaff(staffDetailsDTO);
        if(result.equals("Staff added to system")){
            return ResponseEntity.ok(result);
        }
        return  ResponseEntity.badRequest().body(result);
    }

    @PostMapping(value = "/get-doctor-details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDoctorDetails(@Valid @RequestBody DoctorHPR doctorHPR) {
        String details = doctorService.getDoctorDetails(doctorHPR.getHprId(),doctorHPR.getPassword());
        return  ResponseEntity.ok(details);
    }
    @PostMapping("/validate")
    public ResponseEntity<?> validateT(@RequestParam String token){
         return authService.validateToken(token);
    }




}

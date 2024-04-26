package org.had.accountservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.internet.MimeMessage;
import org.had.accountservice.entity.*;
import org.had.accountservice.exception.MyWebClientException;
import org.had.accountservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    private UserCredentialRepository repository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private DoctorDetailsRepository doctorDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private WebClient webClient;

    @Autowired
    private EmailRegistrationRepository emailRegistrationRepository;

    @Autowired
    private HospitalDetailsRepository hospitalDetailsRepository;
    @Autowired
    private UserCredentialRepository userCredentialRepository;
    @Autowired
    private StaffDetailsRepository staffDetailsRepository;


    public String generateToken(String username, String role) {
        return jwtService.generateToken(username, role);
    }

    public ResponseEntity<?> validateToken(String token, UserDetails userDetails) {
        try {
            jwtService.validateToken(token, userDetails);
            return ResponseEntity.ok("Token valid");
        } catch (Exception e) {
            return new ResponseEntity<>("tokenInvalid", HttpStatus.BAD_REQUEST);
        }
    }

    public String getAuthoritiesAsString(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(""));
    }

    public String sendMail(JsonNode jsonNode) {
        String to = jsonNode.get("to").asText();
        String role = jsonNode.get("role").asText();
        String subject = "Invitation to join our clinic as a " + role.toLowerCase();
        String uuid = String.valueOf(UUID.randomUUID());
        boolean flag = Objects.equals(role, "HEAD_DOCTOR");
        EmailRegistrationDetails emailRegistrationDetails = new EmailRegistrationDetails(uuid, role);
        emailRegistrationDetails.setRole(role);
        emailRegistrationDetails.setToken(uuid);
        emailRegistrationRepository.save(emailRegistrationDetails);
        String url = "http://localhost:5173/register/" + "?role=" + role + "&token=" + uuid + "&flag=" + flag;
        String htmlBody = "<html><body>" + "<p>This is a notification email. We would like you register to our hospital as a </p>" + role.toLowerCase() + "<p><em>" + "</em></p>" + url + "<p><b>This link will valid for only 10 minutes</b></p>" + "</body></html>";
        String body = htmlBody;
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // true indicates multipart message
            helper.setFrom("noreply@example.com"); // Set your no-reply email address
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true indicates HTML content
            javaMailSender.send(message);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
        return "Email Sent";
    }


    public String registerFacility(JsonNode jsonNode) {
        String address = jsonNode.get("address").asText();
        String district = jsonNode.get("district").asText();
        String state = jsonNode.get("state").asText();
        String facilityId = jsonNode.get("facilityId").asText();
        String facilityName = jsonNode.get("facilityName").asText();
        Integer pincode = jsonNode.get("pincode").asInt();
        String specialization = jsonNode.get("specialization").asText();
        String bridgeId = jsonNode.get("bridgeId").asText();

        HospitalDetails hospitalDetails = new HospitalDetails();
        hospitalDetails.setAddress(address);
        hospitalDetails.setHospital_name(facilityName);
        hospitalDetails.setHospital_id(facilityId);
        hospitalDetails.setPincode(pincode);
        hospitalDetails.setSpecialization(specialization);

        String[] districtParts = district.split("-");
        String districtName = districtParts[0];
        String districtCode = districtParts[1];
        String[] stateParts = state.split("-");
        String stateName = stateParts[0];
        String stateCode = stateParts[1];
        hospitalDetails.setDistrict(districtName);
        hospitalDetails.setState(stateName);
        hospitalDetails.setDistrict_code(Integer.valueOf(districtCode));
        hospitalDetails.setState_code(Integer.valueOf(stateCode));
        hospitalDetailsRepository.save(hospitalDetails);

        var values = new HashMap<String, String>() {{
            put("facilityName", facilityName);
            put("facilityId", facilityId);
            put("bridgeId", bridgeId);
        }};

        String requestBody;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("hwello");
        return webClient.post().uri("http://127.0.0.1:9008/abdm/hpr/registerFacility").contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(requestBody)).retrieve().onStatus(HttpStatusCode::isError, clientResponse -> {
            return clientResponse.bodyToMono(String.class).flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
        }).bodyToMono(String.class).block();
    }

    public String verifyEmail(JsonNode jsonNode) {
        String role = jsonNode.get("role").asText();
        String isHeadDoctor = jsonNode.get("isHeadDoctor").asText();
        String token = jsonNode.get("token").asText();

        EmailRegistrationDetails emailRegistrationDetails = emailRegistrationRepository.findByToken(token);

        if (emailRegistrationDetails == null) {
            return "Invalid Token";
        }

        Calendar calendar = Calendar.getInstance();
        if (emailRegistrationDetails.getExpiration().getTime() - calendar.getTime().getTime() <= 0) {
            emailRegistrationRepository.delete(emailRegistrationDetails);
            return "Verification Token Expired";
        }
        emailRegistrationRepository.delete(emailRegistrationDetails);
        return "User Token Verified";
    }

    public void editDetails(JsonNode jsonNode) {
        String role = jsonNode.get("role").asText();
        String username = jsonNode.get("username").asText();
        if (userCredentialRepository.findByUsername(username).isPresent()) {
            UserCredential userCredential = userCredentialRepository.findByUsername(username).get();
            if (role.equals("staff")) {
                editStaffDetails(userCredential, jsonNode);
            } else {
                editDoctorDetails(userCredential, jsonNode);
            }
        }

    }

    private void editDoctorDetails(UserCredential userCredential, JsonNode jsonNode) {
        if (doctorDetailsRepository.findByLoginCredential(userCredential).isPresent()) {
            DoctorDetails doctorDetails = doctorDetailsRepository.findByLoginCredential(userCredential).get();
            doctorDetails.setAddress(jsonNode.get("addressLine").asText());
            doctorDetails.setDistrict(jsonNode.get("district").asText());
            doctorDetails.setState(jsonNode.get("state").asText());
            doctorDetails.setPincode(jsonNode.get("pincode").asInt());
            doctorDetails.setMobile(jsonNode.get("mobileNumber").asText());
            doctorDetailsRepository.save(doctorDetails);
        }
    }

    private void editStaffDetails(UserCredential userCredential, JsonNode jsonNode) {
        if (staffDetailsRepository.findByLoginCredential(userCredential).isPresent()) {
            StaffDetails staffDetails = staffDetailsRepository.findByLoginCredential(userCredential).get();
            staffDetails.setAddress(jsonNode.get("addressLine").asText());
            staffDetails.setDistrict(jsonNode.get("district").asText());
            staffDetails.setState(jsonNode.get("state").asText());
            staffDetails.setPincode(jsonNode.get("pincode").asText());
            staffDetails.setMobile(jsonNode.get("mobileNumber").asText());
            staffDetailsRepository.save(staffDetails);
        }
    }
}

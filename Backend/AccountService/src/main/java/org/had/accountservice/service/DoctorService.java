package org.had.accountservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.had.accountservice.dto.DoctorDetailsDTO;
import org.had.accountservice.entity.DoctorDetails;
import org.had.accountservice.entity.UserCredential;
import org.had.accountservice.exception.MyWebClientException;
import org.had.accountservice.repository.DoctorDetailsRepository;
import org.had.accountservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private WebClient webClient;

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private DoctorDetailsRepository doctorDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Environment environment;

    @Value("${abdm.url}")
    private String abdmUrl;

    public String getDoctorDetails(String otp, String txnId){
        var values = new HashMap<String, String>() {{
            put("otp",otp);
            put("txnId",txnId);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return webClient.post().uri(abdmUrl+"/abdm/hpr/getdoctordetails")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }

    public String generateAadharOTPHPR(String hprId){
        var values = new HashMap<String, String>() {{
            put("hprId",hprId);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return webClient.post().uri(abdmUrl+"/abdm/hpr/generateAadharOTPHPR")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value())));
                })
                .bodyToMono(String.class).block();
    }


    public String addDoctor(DoctorDetailsDTO doctorDetailsDTO) {

        if (userCredentialRepository.findByUsername(doctorDetailsDTO.getUsername()).isPresent()) {
            return "Username is already taken";
        }

        UserCredential userCredential = new UserCredential();
        userCredential.setUsername(doctorDetailsDTO.getUsername());
        userCredential.setPassword(passwordEncoder.encode(doctorDetailsDTO.getPassword()));
        if(doctorDetailsDTO.getIsHeadDoctor()){
            userCredential.setRole("HEAD_DOCTOR");
        }
        else {
            userCredential.setRole("DOCTOR");
        }

        userCredential = userCredentialRepository.save(userCredential);

        DoctorDetails doctorDetails = getDoctorDetails(doctorDetailsDTO, userCredential);
        doctorDetailsRepository.save(doctorDetails);

        return "Doctor added to system";
    }

    public List<DoctorDetails> getdoctorDetailsList(){
        return doctorDetailsRepository.findAll();
    }



    private static DoctorDetails getDoctorDetails(DoctorDetailsDTO doctorDetailsDTO, UserCredential userCredential) {
        DoctorDetails doctorDetails = new DoctorDetails();
        doctorDetails.setHpr_Id(doctorDetailsDTO.getHpr_Id());
        doctorDetails.setRegistration_number(doctorDetailsDTO.getRegistration_number());
        doctorDetails.setFirst_Name(doctorDetailsDTO.getFirst_Name());
        doctorDetails.setLast_Name(doctorDetailsDTO.getLast_Name());
        doctorDetails.setMobile(doctorDetailsDTO.getMobile());
        doctorDetails.setDob(doctorDetailsDTO.getDob());
        doctorDetails.setGender(doctorDetailsDTO.getGender());
        doctorDetails.setState_Code(doctorDetailsDTO.getState_Code());
        doctorDetails.setState(doctorDetailsDTO.getState());
        doctorDetails.setDistrict_Code(doctorDetailsDTO.getDistrict_Code());
        doctorDetails.setDistrict(doctorDetailsDTO.getDistrict());
        doctorDetails.setPincode(doctorDetailsDTO.getPincode());
        doctorDetails.setIsHeadDoctor(doctorDetailsDTO.getIsHeadDoctor());
        doctorDetails.setAddress(doctorDetailsDTO.getAddress());
        doctorDetails.setLoginCredential(userCredential);
        return doctorDetails;
    }

    public DoctorDetails getDoctorDetailsByUsername(String username) {
        System.out.println(username);
        return doctorDetailsRepository.findByLoginCredential(userCredentialRepository.findByUsername(username).get()).get();
    }

//    public String registerHeadDoctor(DoctorDetailsDTO doctorDetailsDTO) {
//
//    }
}

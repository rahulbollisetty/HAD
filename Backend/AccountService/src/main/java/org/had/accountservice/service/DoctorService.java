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

    public String getDoctorDetails(String hprId, String password){
        var values = new HashMap<String, String>() {{
            put("hprId",hprId);
            put("password",password);
        }};

        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return webClient.post().uri("http://127.0.0.1:9008/abdm/hpr/getdoctordetails")
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

}

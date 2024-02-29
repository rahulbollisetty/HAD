package org.had.accountservice.service;

import org.had.accountservice.dto.StaffDetailsDTO;
import org.had.accountservice.entity.StaffDetails;
import org.had.accountservice.entity.UserCredential;
import org.had.accountservice.repository.StaffDetailsRepository;
import org.had.accountservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class StaffService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private StaffDetailsRepository staffDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String addStaff(StaffDetailsDTO staffDetailsDTO){
        if (userCredentialRepository.findByUsername(staffDetailsDTO.getUsername()).isPresent()) {
            return "Username is already taken";
        }

        UserCredential userCredential = new UserCredential();
        userCredential.setUsername(staffDetailsDTO.getUsername());
        userCredential.setPassword(passwordEncoder.encode(staffDetailsDTO.getPassword()));
        userCredential.setRole("STAFF");
        userCredential = userCredentialRepository.save(userCredential);

        StaffDetails staffDetails = getStaffDetails(staffDetailsDTO);
        staffDetailsRepository.save(staffDetails);

        return "Staff added to system";

    }

    private static StaffDetails getStaffDetails(StaffDetailsDTO staffDetailsDTO) {
        StaffDetails staffDetails = new StaffDetails();
        staffDetails.setFirst_Name(staffDetailsDTO.getFirst_Name());
        staffDetails.setLast_Name(staffDetailsDTO.getLast_Name());
        staffDetails.setDob(staffDetailsDTO.getDob());
        staffDetails.setGender(staffDetailsDTO.getGender());
        staffDetails.setState(staffDetails.getState());
        staffDetails.setDistrict(staffDetailsDTO.getDistrict());
        staffDetails.setPincode(staffDetails.getPincode());
        staffDetails.setAddress(staffDetailsDTO.getAddress());
        return staffDetails;
    }
}

package org.had.accountservice.service;

import org.had.accountservice.dto.StaffDetailsDTO;
import org.had.accountservice.entity.DoctorDetails;
import org.had.accountservice.entity.StaffDetails;
import org.had.accountservice.entity.UserCredential;
import org.had.accountservice.repository.DoctorDetailsRepository;
import org.had.accountservice.repository.StaffDetailsRepository;
import org.had.accountservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StaffService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private StaffDetailsRepository staffDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DoctorDetailsRepository doctorDetailsRepository;

    public String addStaff(StaffDetailsDTO staffDetailsDTO){
        if (userCredentialRepository.findByUsername(staffDetailsDTO.getUsername()).isPresent()) {
            return "Username is already taken";
        }

        UserCredential userCredential = new UserCredential();
        userCredential.setUsername(staffDetailsDTO.getUsername());
        userCredential.setPassword(passwordEncoder.encode(staffDetailsDTO.getPassword()));

        userCredentialRepository.save(userCredential);

        StaffDetails staffDetails = getStaffDetails(staffDetailsDTO, userCredential);
        staffDetails.setLoginCredential(userCredential);
        staffDetails.setState(staffDetailsDTO.getState());
        staffDetails.setMobile(staffDetailsDTO.getMobile());
        staffDetails.setPincode(staffDetailsDTO.getPincode());
        staffDetailsRepository.save(staffDetails);

        return "Staff added to system";
    }

    private static StaffDetails getStaffDetails(StaffDetailsDTO staffDetailsDTO, UserCredential userCredential) {
        StaffDetails staffDetails = new StaffDetails();
        staffDetails.setFirst_Name(staffDetailsDTO.getFirst_Name());
        staffDetails.setLast_Name(staffDetailsDTO.getLast_Name());
        staffDetails.setDob(staffDetailsDTO.getDob());
        staffDetails.setGender(staffDetailsDTO.getGender());
        staffDetails.setState(staffDetails.getState());
        staffDetails.setDistrict(staffDetailsDTO.getDistrict());
        staffDetails.setPincode(staffDetails.getPincode());
        staffDetails.setAddress(staffDetailsDTO.getAddress());
//        StaffDetails.setMobile(staffDetails.getMobile());
        return staffDetails;
    }

    public StaffDetails getStaffDetailsByUsername(String username) {
        return staffDetailsRepository.findByLoginCredential(userCredentialRepository.findByUsername(username).get()).get();
    }


}

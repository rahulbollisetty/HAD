package org.had.accountservice.service;

//import com.netflix.discovery.converters.Auto;
import org.had.accountservice.config.UserCredentialUserDetails;
import org.had.accountservice.entity.UserCredential;
import org.had.accountservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCredentialUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> userCredential = userCredentialRepository.findByUsername(username);
        return userCredential.map(UserCredentialUserDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("username not found" + username));
    }

}

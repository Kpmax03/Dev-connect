package com.dev.connect.service.impl;

import com.dev.connect.entity.CustomUserDetail;
import com.dev.connect.entity.User;
import com.dev.connect.exception.ResourceNotFoundException;
import com.dev.connect.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("inside loadbyusername"+username);
        User user = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("email not found"));
        log.info("{}",user);

        return new CustomUserDetail(user);
    }
}

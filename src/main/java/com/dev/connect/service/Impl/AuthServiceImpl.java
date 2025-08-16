package com.dev.connect.service.Impl;

import com.dev.connect.RequestDto.JwtRequest;
import com.dev.connect.ResponseDto.JwtResponse;
import com.dev.connect.jwt.JwtUtil;
import com.dev.connect.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public JwtResponse login(JwtRequest jwtRequest) {

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String token = jwtUtil.generateToken(jwtRequest.getUserName());
        return new JwtResponse(token);

    }

}

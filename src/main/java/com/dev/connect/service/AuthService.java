package com.dev.connect.service;

import com.dev.connect.RequestDto.JwtRequest;
import com.dev.connect.ResponseDto.JwtResponse;

public interface AuthService {
    public JwtResponse login(JwtRequest jwtRequest);
}

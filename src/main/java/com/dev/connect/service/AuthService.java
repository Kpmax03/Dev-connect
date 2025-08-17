package com.dev.connect.service;

import com.dev.connect.RequestDto.JwtRequest;
import com.dev.connect.RequestDto.UserRequest;
import com.dev.connect.ResponseDto.JwtResponse;
import com.dev.connect.ResponseDto.UserResponse;
import com.dev.connect.entity.User;

import java.security.Principal;

public interface AuthService {
    public JwtResponse login(JwtRequest jwtRequest);
    public UserResponse registerUser(UserRequest userRequest);
    public User getCurrentUser(Principal principal);
}

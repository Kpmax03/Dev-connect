package com.dev.connect.Controller;

import com.dev.connect.RequestDto.JwtRequest;
import com.dev.connect.RequestDto.UserRequest;
import com.dev.connect.ResponseDto.JwtResponse;
import com.dev.connect.ResponseDto.UserResponse;
import com.dev.connect.entity.User;
import com.dev.connect.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest){
       return new ResponseEntity<>(authService.login(jwtRequest), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest userRequest){
        return new ResponseEntity<>(authService.registerUser(userRequest), HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getMyAuth(Principal principal){
        return new ResponseEntity<>(authService.getCurrentUser(principal),HttpStatus.OK);
    }

}

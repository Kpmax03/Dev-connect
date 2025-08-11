package com.dev.connect.Controller;

import com.dev.connect.ApiResponse.PageableResponse;
import com.dev.connect.RequestDto.UserRequest;
import com.dev.connect.ResponseDto.UserResponse;
import com.dev.connect.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest userRequest){
        return new ResponseEntity<>(userService.registerUser(userRequest), HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<UserResponse>updateUser(@Valid @RequestBody UserRequest userRequest,Principal principal){
        return new ResponseEntity<>(userService.updateUser(userRequest,principal),HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String>deleteUser(Principal principal){
        return new ResponseEntity<>(userService.deleteUser(principal),HttpStatus.OK);
    }

    @GetMapping("/byId/{userId}")
    public ResponseEntity<UserResponse>getUserById(@PathVariable String userId){
        return new ResponseEntity<>(userService.getById(userId),HttpStatus.FOUND);
    }

    @GetMapping("/getAll")
    public ResponseEntity<PageableResponse<UserResponse>>getAllUser(
            @RequestParam(defaultValue = "0",required = false) int pageNumber,
            @RequestParam(defaultValue = "10",required = false) int pageSize,
            @RequestParam(defaultValue = "id",required = false) String sortBy
    ){

        return new ResponseEntity<>(userService.getAll(pageNumber,pageSize,sortBy),HttpStatus.OK);
    }




   //admin only
    @PutMapping("/admin/update/{userId}")
    public ResponseEntity<UserResponse>adminUpdateUser(@PathVariable String userId,@Valid @RequestBody UserRequest userRequest,Principal principal){
        return new ResponseEntity<>(userService.adminUpdateUser(userId,userRequest),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/admin/delete/{userId}")
    public ResponseEntity<String>adminDeleteUser(@PathVariable String userId,Principal principal){
        return new ResponseEntity<>(userService.adminDeleteUser(userId),HttpStatus.OK);
    }

}

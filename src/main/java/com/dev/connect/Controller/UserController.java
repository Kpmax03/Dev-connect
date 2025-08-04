package com.dev.connect.Controller;

import com.dev.connect.ApiResponse.PageableResponse;
import com.dev.connect.dto.UserDto;
import com.dev.connect.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.registerUser(userDto), HttpStatus.CREATED);
    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto>updateUser(@PathVariable String userId,@Valid @RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.updateUser(userId,userDto),HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String>deleteUser(@PathVariable String userId){
        return new ResponseEntity<>(userService.deleteUser(userId),HttpStatus.OK);
    }
    @GetMapping("/byId/{userId}")
    public ResponseEntity<UserDto>getUserById(@PathVariable String userId){
        return new ResponseEntity<>(userService.getById(userId),HttpStatus.FOUND);
    }
    @GetMapping("/getAll")
    public ResponseEntity<PageableResponse<UserDto>>getAllUser(@RequestParam(defaultValue = "0") int pagenumber
            , @RequestParam(defaultValue = "10") int pagesize
            , @RequestParam(defaultValue = "id") String sortby){

        return new ResponseEntity<>(userService.getAll(pagenumber,pagesize,sortby),HttpStatus.OK);
    }

}
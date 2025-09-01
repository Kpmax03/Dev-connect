package com.dev.connect.Controller;

import com.dev.connect.apiResponse.PageableResponse;
import com.dev.connect.RequestDto.UserRequest;
import com.dev.connect.ResponseDto.UserResponse;
import com.dev.connect.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/user")
@Tag(name = "operations based on user")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/update")
    @Operation(summary = "update user")
    public ResponseEntity<UserResponse>updateUser(@Valid @RequestBody UserRequest userRequest,Principal principal){
        return new ResponseEntity<>(userService.updateUser(userRequest,principal),HttpStatus.ACCEPTED);
    }

    @Operation(summary = "delete user")
    @DeleteMapping("/delete")
    public ResponseEntity<String>deleteUser(Principal principal){
        return new ResponseEntity<>(userService.deleteUser(principal),HttpStatus.OK);
    }

    @Operation(summary = "getuser by their id")
    @GetMapping("/byId/{userId}")
    public ResponseEntity<UserResponse>getUserById(@PathVariable String userId){
        return new ResponseEntity<>(userService.getById(userId),HttpStatus.FOUND);
    }
    @Operation(summary = "get all users")
    @GetMapping("/getAll")
    public ResponseEntity<PageableResponse<UserResponse>>getAllUser(
            @RequestParam(defaultValue = "0",required = false) int pageNumber,
            @RequestParam(defaultValue = "10",required = false) int pageSize,
            @RequestParam(defaultValue = "id",required = false) String sortBy
    ){

        return new ResponseEntity<>(userService.getAll(pageNumber,pageSize,sortBy),HttpStatus.OK);
    }

    // searching user from specific tags and domain
    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> searchUserByDomainOrTechsOrDomainAndTechs(
            @RequestParam(required = false) String domain,
            @RequestParam(required = false) List<String> techs
    ){
        // search for only domain not techs
        if((domain!=null && !domain.isEmpty())&&(techs==null || techs.isEmpty())){

            return ResponseEntity.ok(userService.searchUserByDomain(domain));
        //search for only techs not domain
        } else if ((domain == null || domain.isEmpty()) && (techs!=null && !techs.isEmpty())) {

            return ResponseEntity.ok(userService.searchUserByTechs(techs));
        // search for domain also and techs also
        }else {
            return ResponseEntity.ok(userService.searchUserByDomainAndTechs(domain,techs));
        }
    }


   // admin only
    @PutMapping("/admin/update/{userId}")
    public ResponseEntity<UserResponse>adminUpdateUser(@PathVariable String userId,@Valid @RequestBody UserRequest userRequest,Principal principal){
        return new ResponseEntity<>(userService.adminUpdateUser(userId,userRequest),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/admin/delete/{userId}")
    public ResponseEntity<String>adminDeleteUser(@PathVariable String userId,Principal principal){
        return new ResponseEntity<>(userService.adminDeleteUser(userId),HttpStatus.OK);
    }

}

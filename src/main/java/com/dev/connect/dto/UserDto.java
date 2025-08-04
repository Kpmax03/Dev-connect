package com.dev.connect.dto;

import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.*;
import java.time.LocalDate;

@Setter
@Getter
public class UserDto {
    private String id;
    @Size(min = 2,max = 10,message = "name should be greater than 2 or smaller than 11")
    private String firstName;
    @Size(min = 4,max = 15,message = "lastname should be in between 4 to 15")
    private String lastName;
    @Min(value = 14,message = "illegle age ! ")
    @Max(value = 60,message = "age is to old for todays generation")
    private int age;
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",message = "not valid email")
    private String email;
    @NotEmpty
    @Size(min = 2,max = 15,message = "password should be in between 2 to 15")
    private String password;
    private String role;
    private String bio;
    private int followers;
    private int following;
    private LocalDate createdAt;
}
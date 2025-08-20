package com.dev.connect.RequestDto;

import com.dev.connect.commonDto.RoleDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter@Setter@ToString@Builder
public class UserRequest {
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",message = "not valid email")
    private String email;
    @NotEmpty
    @Size(min = 2,max = 15,message = "password should be in between 2 to 15")
    private String password;
    private List<RoleDto> roleDtoList =new ArrayList<>();
    private UserProfileRequest userProfileRequestDto;

}

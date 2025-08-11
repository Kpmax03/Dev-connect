package com.dev.connect.ResponseDto;

import com.dev.connect.dto.RoleDto;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString@Builder
public class UserResponse {
    private String id;
    private String email;
    private String password;
    private UserProfileResponse userProfileResponseDto;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private List<RoleDto> role=new ArrayList<>();
    private List<ShortPost> posts=new ArrayList<>();
}

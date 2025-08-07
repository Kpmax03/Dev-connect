package com.dev.connect.ResponseDto;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString@Builder
public class UserResponse {
    private String id;
    private String email;
    private String password;
    private String role;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private UserProfileResponse userProfileResponseDto;
    private List<ShortPost> posts=new ArrayList<>();
}

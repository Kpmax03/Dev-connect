package com.dev.connect.ResponseDto;

import lombok.*;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString
public class UserProfileResponse {
    private int profileId;

    private String firstName;

    private String lastName;

    private int age;

    private String bio;

    private String gitHubLink;

    private String linkedInLink;

    private String gender;

    private String userId;
}

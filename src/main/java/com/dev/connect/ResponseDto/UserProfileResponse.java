package com.dev.connect.ResponseDto;

import com.dev.connect.enums.Domain;
import com.dev.connect.enums.Techs;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString
public class UserProfileResponse {

    private int profileId;

    private String firstName;

    private String lastName;

    private String Domain;

    private String techs;

    private int age;

    private String gitHubLink;

    private String linkedInLink;

    private String gender;

    private String userId;
}

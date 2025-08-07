package com.dev.connect.RequestDto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class UserProfileRequest {
    @Size(min = 2,max = 10,message = "name should be greater than 2 or smaller than 11")
    private String firstName;

    @Size(min = 4,max = 15,message = "lastname should be in between 4 to 15")
    private String lastName;

    @Min(value = 14,message = "illegle age ! ")
    @Max(value = 60,message = "age is to old for todays generation")
    private int age;

    private String bio;

    // @Pattern(regexp = "^https:\\/\\/github\\.com\\/[A-Za-z0-9_-]{1,39}$",message = "invalid github profile url")
    private String gitHubLink;
    // @Pattern(regexp = "^https:\\/\\/(www\\.)?linkedin\\.com\\/in\\/[-a-zA-Z0-9_]{3,100}$",message = "invalid linkedin profile url")
    private String linkedInLink;

    private String gender;

}

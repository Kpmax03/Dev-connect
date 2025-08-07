package com.dev.connect.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@Builder
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int profileId;
    private String firstName;
    private String lastName;
    private int age;
    private String bio;
    private String gitHubLink;
    private String linkedInLink;
    private String gender;
    @OneToOne(mappedBy ="userProfile")
    private User user;
}

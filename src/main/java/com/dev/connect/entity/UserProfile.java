package com.dev.connect.entity;

import com.dev.connect.enums.Domain;
import com.dev.connect.enums.Techs;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@Builder
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int profileId;
    private String firstName;
    private String lastName;
    private int age;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_domain")
    private Set<Domain> domain=new HashSet<>();

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_techs")
    private Set<Techs> techs=new HashSet<>();

    private String gitHubLink;
    private String linkedInLink;
    private String gender;

    @OneToOne(mappedBy ="userProfile")
    private User user;

}

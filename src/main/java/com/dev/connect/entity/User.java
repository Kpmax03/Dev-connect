package com.dev.connect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor@NoArgsConstructor
public class User {
    @Id
    @Column(name = "userId",unique = false,nullable = false)
    private String id;

    @Column(unique = true,nullable = false)
    private String email;

    private String password;

    @Column(length = 15)
    private String role;

    @Column(updatable = false)
    private LocalDate createdAt;

    private LocalDate updatedAt;

    @OneToOne(orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name="profile_id")
    private UserProfile userProfile;

    @OneToMany(mappedBy = "user",orphanRemoval = true)
    private List<Post> post=new ArrayList<>();
}

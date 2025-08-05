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
    @Column(name = "user_id",unique = false,nullable = false)
    private String id;

    @Column(length = 10,nullable = false)
    private String firstName;

    @Column(length = 25,nullable = true)
    private String lastName;

    @Column(length = 100)
    private int age;

    @Column(unique = true,nullable = false)
    private String email;

    private String password;

    @Column(length = 15)
    private String role;

    @Column(length = 50)
    private String bio;
    private LocalDate createdAt;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Post> post=new ArrayList<>();
}

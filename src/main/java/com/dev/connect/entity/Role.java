package com.dev.connect.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@Builder
public class Role {
    @Id
    @Column(name="Role_id")
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;
    private String name;

    @ManyToMany(mappedBy = "role")
    private List<User>user=new ArrayList<>();
}

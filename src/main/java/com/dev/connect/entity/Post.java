package com.dev.connect.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "post")
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@Builder@ToString
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int postId;
    @Column(length = 20)
    private String type;
    @Column(length = 25)
    private String title;
    @Column(length = 1000)
    private String content;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    @ManyToOne()
    @JoinColumn(name="userId")
    private User user;

}

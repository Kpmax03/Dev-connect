package com.dev.connect.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
public class Comment {
    @Id
    private String commentId;

    private String content;

    private LocalDate commentedAt;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;
}

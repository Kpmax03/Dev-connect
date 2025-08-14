package com.dev.connect.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Entity
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@Builder@ToString
public class Comment {
    @Id
    private String commentId;

    private String content;

    private LocalDate commentedAt;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;
}

package com.dev.connect.entity;

import com.dev.connect.enums.PostType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "post")
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@Builder@ToString
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int postId;

    @Enumerated(EnumType.STRING)
    private PostType type;

    @Column(length = 25)
    private String title;

    @Column(length = 1000)
    private String content;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    @ManyToOne()
    @JoinColumn(name="userId")
    private User user;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "post_tags")
    private Set<String> tags=new HashSet<>();

    @OneToMany(mappedBy = "post",orphanRemoval = true)
    private List<Comment> commentList=new ArrayList<>();

}

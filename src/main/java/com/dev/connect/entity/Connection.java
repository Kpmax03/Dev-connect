package com.dev.connect.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString
public class Connection {
    @Id
    private String connectionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    private User following;

}

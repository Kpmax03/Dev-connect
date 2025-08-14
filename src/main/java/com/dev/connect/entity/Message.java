package com.dev.connect.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString@Builder
public class Message {
    @Id
    private String messageId;

    private String content;

    private LocalDate messegedAt;

    @ManyToOne()
    @JoinColumn
    private User sender;

    @ManyToOne()
    @JoinColumn()
    private User receiver;

}

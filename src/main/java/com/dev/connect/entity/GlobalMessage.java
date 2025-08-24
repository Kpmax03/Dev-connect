package com.dev.connect.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@AllArgsConstructor@NoArgsConstructor@Getter@Setter@Builder
public class GlobalMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;

    private String sender;
    private String content;
    private LocalDateTime timeStamp;

}

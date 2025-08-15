package com.dev.connect.ResponseDto;

import com.dev.connect.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@Builder@ToString
public class MessageResponse {

    private String messageId;

    private String title;

    private String content;

    private LocalDateTime messegedAt;

    private String senderId;

    private String receiverId;

}

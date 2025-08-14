package com.dev.connect.ResponseDto;

import com.dev.connect.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@Builder@ToString
public class MessageResponse {

    private String messageId;

    private String content;

    private LocalDate messegedAt;

    private User sender;

    private User receiver;

}

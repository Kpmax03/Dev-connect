package com.dev.connect.ResponseDto;

import lombok.*;

import java.time.LocalDate;
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString@Builder
public class CommentResponse {

    private String commentId;
    private String content;
    private LocalDate commentedAt;
    private String userId;
    private int postId;

}

package com.dev.connect.ResponseDto;

import lombok.*;

import java.time.LocalDate;
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString
public class PostResponse {
    private int postId;
    private String type;
    private String title;
    private String content;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String userId;
}

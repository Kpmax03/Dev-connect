package com.dev.connect.ResponseDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString@Builder
public class CommentResponse {

    private String commentId;
    private String content;

    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private LocalDate commentedAt;

    private String userId;
    private int postId;

}

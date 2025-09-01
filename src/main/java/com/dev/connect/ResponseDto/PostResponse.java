package com.dev.connect.ResponseDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString
public class PostResponse {
    private int postId;
    private String type;
    private String title;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private LocalDate createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private LocalDate updatedAt;

    private String userId;
    private Long comments;
    private String tags;

}

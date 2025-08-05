package com.dev.connect.dto;

import com.dev.connect.entity.Post;
import com.dev.connect.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Setter@Getter@Builder
@AllArgsConstructor@NoArgsConstructor@ToString
public class PostDto {
    private int postId;
    private String type;
    @Min(value = 4,message = "tile should be greater then 4 letters")
    @Max(value = 15,message = "tile should be smaller then 15 letters")
    private String title;
    @Min(value = 5,message = "content should be greater then 5 letters")
    private String content;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    @NotEmpty
    private String user_id;
}
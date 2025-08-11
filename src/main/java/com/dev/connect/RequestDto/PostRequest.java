package com.dev.connect.RequestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class PostRequest {
    private String type;
    @Size(min=4,max=20 ,message = "title must be between 4-20")
    private String title;
    @Size(min = 5,message = "content should be greater then 5 letters")
    private String content;

}

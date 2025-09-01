package com.dev.connect.RequestDto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@Builder
public class PostRequest {

    private String type;

    @Size(min=4,max=20 ,message = "title must be between 4-20")
    private String title;

    @Size(min = 5,message = "content should be greater then 5 letters")
    private String content;

    private Set<String> tags=new HashSet<>();

}

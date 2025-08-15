package com.dev.connect.RequestDto;

import lombok.*;

@Getter@Setter@Builder@AllArgsConstructor@NoArgsConstructor
public class MessageRequest {

    private String title;
    private String content;

}

package com.dev.connect.ResponseDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor@NoArgsConstructor@Getter@Setter@ToString@Builder
public class GlobalMessageResponse {

    private int messageId;
    private String sender;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH-mm")
    private LocalDateTime timeStamp;

}

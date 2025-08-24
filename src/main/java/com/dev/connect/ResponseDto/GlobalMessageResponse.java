package com.dev.connect.ResponseDto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor@NoArgsConstructor@Getter@Setter@ToString@Builder
public class GlobalMessageResponse {

    private int messageId;
    private String sender;
    private String content;
    private LocalDateTime timeStamp;

}

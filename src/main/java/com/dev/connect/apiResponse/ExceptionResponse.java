package com.dev.connect.apiResponse;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExceptionResponse {
    private String message;
    private String status;
    private LocalDateTime datetime;
}

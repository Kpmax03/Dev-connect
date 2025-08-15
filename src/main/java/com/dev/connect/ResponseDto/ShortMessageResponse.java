package com.dev.connect.ResponseDto;

import lombok.*;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;

import java.time.LocalDateTime;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@Builder@ToString
public class ShortMessageResponse {
    private String from;
    private String to;
    private String title;
    private LocalDateTime time;
}

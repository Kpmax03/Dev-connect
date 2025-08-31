package com.dev.connect.ResponseDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;

import java.time.LocalDateTime;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@Builder@ToString
public class ShortMessageResponse {
    private String from;
    private String to;
    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH-mm")
    private LocalDateTime time;
}

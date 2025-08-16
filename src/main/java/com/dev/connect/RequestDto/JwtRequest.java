package com.dev.connect.RequestDto;

import lombok.*;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString@Builder
public class JwtRequest {

    private String userName;
    private String password;

}

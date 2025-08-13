package com.dev.connect.ResponseDto;

import java.time.LocalDate;

public class CommentResponse {
    private String commentId;
    private String content;
    private LocalDate commentedAt;
    private String userId;
    private PostResponse postResponse;
}

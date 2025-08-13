package com.dev.connect.service;

import com.dev.connect.RequestDto.CommentRequest;
import com.dev.connect.ResponseDto.CommentResponse;

import java.security.Principal;

public interface CommentService {
    public CommentResponse createComment(int postId, CommentRequest commentRequest, Principal principal);
    public String deleteComment(String commentId,Principal principal);
    public void seeCommentOfSpecificPost(int postId,Principal principal);
    public String adminDeleteComment(String commentId);
}

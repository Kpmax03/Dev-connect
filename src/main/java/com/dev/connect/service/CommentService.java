package com.dev.connect.service;

import com.dev.connect.RequestDto.CommentRequest;
import com.dev.connect.ResponseDto.CommentResponse;

import java.security.Principal;
import java.util.List;

public interface CommentService {
    public CommentResponse createComment(int postId, CommentRequest commentRequest, Principal principal);
    public String deleteComment(String commentId,Principal principal);
    public List<CommentResponse> seeCommentOfSpecificPost(int postId);
    public String adminDeleteComment(String commentId);
}

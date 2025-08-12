package com.dev.connect.service;

import com.dev.connect.apiResponse.PageableResponse;
import com.dev.connect.RequestDto.PostRequest;
import com.dev.connect.ResponseDto.PostResponse;

import java.security.Principal;

public interface PostService {
    public PostResponse createPost(PostRequest postRequest,Principal principal);
    public PostResponse editPost(int postId,PostRequest postRequest, Principal principal);
    public String deletePost(int postId,Principal principal);
    public PageableResponse<PostResponse> gettAllPost(int pagenumber,int pagesize,String sortBy);
    public PageableResponse<PostResponse>getAllPostOfUser(String userId,int pageNumber,int pageSize,String sortBy);
    public PostResponse getPostById(int PostId);

    public String adminDeletePost(int postId);
    public PostResponse adminEditPost(int postId, PostRequest postRequest);
}

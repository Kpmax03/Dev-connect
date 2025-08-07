package com.dev.connect.service;

import com.dev.connect.ApiResponse.PageableResponse;
import com.dev.connect.RequestDto.PostRequest;
import com.dev.connect.ResponseDto.PostResponse;

public interface PostService {
    public PostResponse createPost(PostRequest postRequest);
    public PostResponse editPost(int postId, PostRequest postRequest);
    public String deletePost(int postId);
    public PageableResponse<PostResponse> gettAllPost(int pagenumber,int pagesize,String sortBy);
    public PageableResponse<PostResponse>getAllPostOfUser(String userId,int pageNumber,int pageSize,String sortBy);
    public PostResponse getPostById(int PostId);
}

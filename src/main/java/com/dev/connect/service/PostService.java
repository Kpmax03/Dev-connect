package com.dev.connect.service;

import com.dev.connect.ApiResponse.PageableResponse;
import com.dev.connect.dto.PostDto;

public interface PostService {
    public PostDto createPost(PostDto postDto);
    public PostDto editPost(int postId,PostDto postDto);
    public String deletePost(int postId);
    public PageableResponse<PostDto> gettAllPost(int pagenumber,int pagesize,String sortBy);
    public PageableResponse<PostDto>getAllPostOfUser(String userId,int pageNumber,int pageSize,String sortBy);
    public PostDto getPostById(int PostId);
}

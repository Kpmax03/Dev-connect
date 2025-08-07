package com.dev.connect.service.Impl;

import com.dev.connect.ApiResponse.PageableResponse;
import com.dev.connect.RequestDto.PostRequest;
import com.dev.connect.ResponseDto.PostResponse;
import com.dev.connect.config.CustomMethods;
import com.dev.connect.entity.Post;
import com.dev.connect.entity.User;
import com.dev.connect.exception.ResourceNotFoundException;
import com.dev.connect.repository.PostRepository;
import com.dev.connect.repository.UserRepository;
import com.dev.connect.service.PostService;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
     @Autowired
     private PostRepository postRepository;
     @Autowired
     private UserRepository userRepository;
     @Autowired
     private ModelMapper mapper;
    @Override
    public PostResponse createPost(PostRequest postRequest) {
        String userId = postRequest.getUserId();

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException());
        Post post=Post.builder().title(postRequest.getTitle()).type(postRequest.getType()).content(postRequest.getContent()).build();

        post.setUser(user);
        post.setCreatedAt(LocalDate.now());
        post.setUpdatedAt(LocalDate.now());

        user.getPost().add(post);

        userRepository.save(user);
        Post save = postRepository.save(post);

        PostResponse postResponse = mapper.map(save, PostResponse.class);

        postResponse.setUserId(userId);

        return postResponse;
    }

    @Override
    public PostResponse editPost(int postId, PostRequest postRequest) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("postid not found"));
        String userId=postRequest.getUserId();
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found"));

        post.setType(postRequest.getType());
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setUpdatedAt(LocalDate.now());

        userRepository.save(user);
        Post save = postRepository.save(post);

        PostResponse postResponse = mapper.map(save, PostResponse.class);

        postResponse.setUserId(userId);

        return postResponse;
    }

    @Override
    public String deletePost(int postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("posId not found "));
        postRepository.delete(post);
        return "deleted post of id : "+postId;
    }

    @Override
    public PageableResponse<PostResponse> gettAllPost(int pagenumber, int pagesize, String sortBy) {
        Pageable pageable= PageRequest.of(pagenumber,pagesize, Sort.by(sortBy));
        Page<Post> page = postRepository.findAll(pageable);
        List<PostResponse> collect = page.stream().map(singlePost -> {
            PostResponse postResponse = mapper.map(singlePost, PostResponse.class);
            postResponse.setUserId(singlePost.getUser().getId());
            return postResponse;
        }).collect(Collectors.toList());
        PageableResponse pageableResponse= CustomMethods.getPageableReponse(collect,page);
        return pageableResponse;
    }

    @Override
    public PageableResponse<PostResponse> getAllPostOfUser(String userId, int pageNumber, int pageSize, String sortBy) {
        Pageable pageable=PageRequest.of(pageNumber,pageSize,Sort.by(sortBy));
        Page<Post> page = postRepository.findAllPostByUser(userId,pageable);
        List<PostResponse> content = page.getContent().stream().map(singlePost->{
            PostResponse postResponse = mapper.map(singlePost, PostResponse.class);
            postResponse.setUserId(singlePost.getUser().getId());
            return postResponse;
        }).collect(Collectors.toList());
        PageableResponse<PostResponse> pageableReponse = CustomMethods.getPageableReponse(content, page);
        return pageableReponse;
    }

    @Override
    public PostResponse getPostById(int postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("id not found"));
        PostResponse postResponse = mapper.map(post, PostResponse.class);
        postResponse.setUserId(post.getUser().getId());
        return postResponse;
    }
}

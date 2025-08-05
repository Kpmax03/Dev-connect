package com.dev.connect.service.Impl;

import com.dev.connect.ApiResponse.PageableResponse;
import com.dev.connect.dto.PostDto;
import com.dev.connect.entity.Post;
import com.dev.connect.entity.User;
import com.dev.connect.exception.ResourceNotFoundException;
import com.dev.connect.repository.PostRepository;
import com.dev.connect.repository.UserRepository;
import com.dev.connect.service.PostService;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserRepository userRepository;


    @Override
    public PostDto createPost(PostDto postDto) {
        postDto.setCreatedAt(LocalDate.now());
        postDto.setUpdatedAt(LocalDate.now());
        Post post = mapper.map(postDto, Post.class);
        User user = userRepository.findById(postDto.getUser_id()).orElseThrow(() -> new ResourceNotFoundException());
        post.setUser(user);
        PostDto createdPostDto = mapper.map(postRepository.save(post), PostDto.class);
        createdPostDto.setUser_id(postDto.getUser_id());
        return createdPostDto;
    }

    @Override
    public PostDto editPost(int postId, PostDto postDto) {
        Post unUpdatedPost = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("cant update because given id doesnot found"));
        unUpdatedPost.setTitle(postDto.getTitle());
        unUpdatedPost.setType(postDto.getType());
        unUpdatedPost.setContent(postDto.getContent());
        unUpdatedPost.setUpdatedAt(LocalDate.now());
        User user = userRepository.findById(postDto.getUser_id()).orElseThrow(() -> new ResourceNotFoundException());
        unUpdatedPost.setUser(user);
        Post updated = postRepository.save(unUpdatedPost);
        PostDto updatedPostDto = mapper.map(postRepository.save(unUpdatedPost), PostDto.class);
        updatedPostDto.setUser_id(postDto.getUser_id());
        return mapper.map(updated,PostDto.class);
    }

    @Override
    public String deletePost(int postId) {
        postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("not found post"));
        postRepository.deleteById(postId);
        return "deleted post of id :"+postId;
    }

    @Override
    public PageableResponse<PostDto> gettAllPost(int pagenumber, int pagesize, String sortBy) {
        Sort sort = Sort.by(sortBy);
        Pageable pageable= PageRequest.of(pagenumber,pagesize,sort);
        Page<Post> page=postRepository.findAll(pageable);
        List<PostDto> collect = page.stream().map(onePost -> {
            PostDto onePostDto= mapper.map(onePost, PostDto.class);
            onePostDto.setUser_id(onePost.getUser().getId());
            return onePostDto;
        }).collect(Collectors.toList());

        PageableResponse<PostDto> response=PageableResponse.<PostDto>builder().content(collect)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalelement(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .isLastPage(page.isLast())
                .build();
        return response;
    }

    @Override
    public PageableResponse<PostDto> getAllPostOfUser(String userId,int pageNumber,int pageSize,String sortBy) {
        Pageable pageable=PageRequest.of(pageNumber,pageSize,Sort.by(sortBy));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        Page<Post> page = postRepository.findByUser(user, pageable);

        List<PostDto> collect = page.stream().map(onePost -> {

            PostDto onePostDto = mapper.map(onePost, PostDto.class);
            onePostDto.setUser_id(onePost.getUser().getId());
            return onePostDto;
        }).collect(Collectors.toList());

        PageableResponse<PostDto> response=PageableResponse.<PostDto>builder().content(collect)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalelement(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .isLastPage(page.isLast())
                .build();

        return response;
    }

    @Override
    public PostDto getPostById(int postId) {
         Post post= postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("user not found"));
         PostDto onePostDto=mapper.map(post,PostDto.class);
        Post post1 = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException());
        onePostDto.setUser_id(post1.getUser().getId());
        return onePostDto;
    }
}

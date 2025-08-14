package com.dev.connect.service.Impl;

import com.dev.connect.apiResponse.PageableResponse;
import com.dev.connect.RequestDto.PostRequest;
import com.dev.connect.ResponseDto.PostResponse;
import com.dev.connect.config.CustomMethods;
import com.dev.connect.entity.Post;
import com.dev.connect.entity.User;
import com.dev.connect.exception.InvalidCradentialException;
import com.dev.connect.exception.ResourceNotFoundException;
import com.dev.connect.repository.CommentRepository;
import com.dev.connect.repository.ConnectionRepository;
import com.dev.connect.repository.PostRepository;
import com.dev.connect.repository.UserRepository;
import com.dev.connect.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
     @Autowired
     private PostRepository postRepository;

     @Autowired
     private UserRepository userRepository;

     @Autowired
     private CommentRepository commentRepository;

     @Autowired
     private ModelMapper mapper;

    @Override
    public PostResponse createPost(PostRequest postRequest,Principal principal) {
        String principalName = principal.getName();
        User user = userRepository.findByEmail(principalName).orElseThrow(() -> new ResourceNotFoundException());

        Post post=Post.builder().title(postRequest.getTitle()).type(postRequest.getType()).content(postRequest.getContent()).build();

        post.setUser(user);
        post.setCreatedAt(LocalDate.now());
        post.setUpdatedAt(LocalDate.now());

        user.getPost().add(post);

        userRepository.save(user);
        Post save = postRepository.save(post);

        PostResponse postResponse = CustomMethods.getPostResponse(save);

        Optional<Long> countByUser = commentRepository.countByUser(user);

        postResponse.setComments(countByUser.get());

        return postResponse;
    }

    @Override
    public PostResponse editPost(int postId,PostRequest postRequest, Principal principal) {
        String name = principal.getName();
        User principleUser = userRepository.findByEmail(name).orElseThrow(() -> new ResourceNotFoundException("user not found"));

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("postid not found"));
        if(!post.getUser().getId().equals(principleUser.getId()))
            throw new InvalidCradentialException("access denied for updating others post");

            post.setType(postRequest.getType());
            post.setTitle(postRequest.getTitle());
            post.setContent(postRequest.getContent());
            post.setUpdatedAt(LocalDate.now());

            userRepository.save(principleUser);
            Post save = postRepository.save(post);

            PostResponse postResponse = CustomMethods.getPostResponse(save);

        Optional<Long> countByUser = commentRepository.countByUser(principleUser);

        return postResponse;
    }

    @Override
    public String deletePost(int postId,Principal principal) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("posId not found "));

        String principalName = principal.getName();
        User user = userRepository.findByEmail(principalName).orElseThrow(() -> new ResourceNotFoundException());

        if(!post.getUser().getId().equals(user.getId()))
            throw new InvalidCradentialException("access denied can't delete others post ");
        postRepository.delete(post);
        return "deleted post of id : "+postId;
    }

    @Override
    public PageableResponse<PostResponse> gettAllPost(int pagenumber, int pagesize, String sortBy) {

        Pageable pageable= PageRequest.of(pagenumber,pagesize, Sort.by(sortBy));

        Page<Post> page = postRepository.findAll(pageable);

        List<PostResponse> collect = page.stream().map(singlePost -> {
            PostResponse postResponse = CustomMethods.getPostResponse(singlePost);
            Optional<Long> countByUser = commentRepository.countByUser(singlePost.getUser());
            postResponse.setComments(countByUser.get());
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
            PostResponse postResponse = CustomMethods.getPostResponse(singlePost);
            Optional<Long> countByUser = commentRepository.countByUser(singlePost.getUser());
            postResponse.setComments(countByUser.get());
            return postResponse;
        }).collect(Collectors.toList());

        PageableResponse<PostResponse> pageableReponse = CustomMethods.getPageableReponse(content, page);

        return pageableReponse;
    }

    @Override
    public PostResponse getPostById(int postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("id not found"));
        PostResponse postResponse = CustomMethods.getPostResponse(post);
        Optional<Long> countByUser = commentRepository.countByUser(post.getUser());

        return postResponse;
    }
     //admin only
    @Override
    public PostResponse adminEditPost(int postId, PostRequest postRequest) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post not found"));

        post.setType(postRequest.getType());
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setUpdatedAt(LocalDate.now());

        Post save = postRepository.save(post);

        PostResponse postResponse = CustomMethods.getPostResponse(save);
        return postResponse;
    }

    @Override
    public String adminDeletePost(int postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post not found"));
        postRepository.delete(post);
        return "deleted post from admin postid ="+postId;
    }

}

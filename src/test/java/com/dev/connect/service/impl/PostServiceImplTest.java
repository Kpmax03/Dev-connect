package com.dev.connect.service.impl;

import com.dev.connect.RequestDto.PostRequest;
import com.dev.connect.ResponseDto.PostResponse;
import com.dev.connect.apiResponse.PageableResponse;
import com.dev.connect.entity.Post;
import com.dev.connect.entity.User;
import com.dev.connect.entity.UserProfile;
import com.dev.connect.repository.CommentRepository;
import com.dev.connect.repository.PostRepository;
import com.dev.connect.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentRepository commentRepository;

    private ModelMapper mapper=new ModelMapper();
    private User user;
    private Post post;
    private Principal principal;
    @BeforeEach
    void setUp() {
        user = User.builder()
                .id("wefghh")
                .email("kp5911800@gmail.com")
                .password("pass")
                .userProfile(new UserProfile())
                .role(new ArrayList<>())
                .post(new ArrayList<>())
                .build();

        post = Post.builder()
                .postId(1)
                .title("jobs")
                .content("job at banglore")
                .type("job")
                .user(user)
                .build();

        principal = () -> "kp5911800@gmail.com";
    }
    @Test
    void createPost() {

        PostRequest postRequest= PostRequest.builder()
                .title("jobs")
                .content("job at banglore")
                .type("job")
                .build();

        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        Mockito.when(postRepository.save(Mockito.any(Post.class))).thenReturn(post);

        Mockito.when(commentRepository.countByPost(Mockito.any(Post.class))).thenReturn(Optional.of(3L));

        PostResponse actualresult = postService.createPost(postRequest, principal);

        Assertions.assertNotNull(actualresult);
        Assertions.assertEquals(postRequest.getTitle(),actualresult.getTitle());

    }

    @Test
    void editPost() {
        PostRequest request = PostRequest.builder()
                .title("contribution")
                .content("contribution in gsoc")
                .type("job")
                .build();

        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(postRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(post));
        Mockito.when(postRepository.save(Mockito.any(Post.class))).thenReturn(post);
        Mockito.when(commentRepository.countByPost(Mockito.any(Post.class))).thenReturn(Optional.of(2L));

        PostResponse response = postService.editPost(1, request, principal);

        Assertions.assertEquals("contribution", response.getTitle());
        Assertions.assertEquals("contribution in gsoc", response.getContent());
    }

    @Test
    void deletePost() {
        Mockito.when(postRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(post));
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

        String result = postService.deletePost(1, principal);

        Assertions.assertTrue(result.contains("deleted post"));
        Mockito.verify(postRepository, Mockito.times(1)).delete(post);
    }

    @Test
    void gettAllPost() {
        Page<Post> page = new PageImpl<>(List.of(post));

        Mockito.when(postRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);
        Mockito.when(commentRepository.countByPost(Mockito.any(Post.class))).thenReturn(Optional.of(3L));

        PageableResponse<PostResponse> response = postService.gettAllPost(0, 10, "title");

        Assertions.assertEquals(1, response.getContent().size());

    }

    @Test
    void getAllPostOfUser() {

        Page<Post> page = new PageImpl<>(List.of(post));

        Mockito.when(postRepository.findAllPostByUser(Mockito.anyString(), Mockito.any(Pageable.class)))
                .thenReturn(page);
        Mockito.when(commentRepository.countByPost(Mockito.any(Post.class))).thenReturn(Optional.of(1L));

        PageableResponse<PostResponse> response =
                postService.getAllPostOfUser("1", 0, 10, "title");

        Assertions.assertEquals(1, response.getContent().size());
    }

    @Test
    void getPostById() {
        Mockito.when(postRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(post));
        Mockito.when(commentRepository.countByPost(Mockito.any(Post.class))).thenReturn(Optional.of(0L));

        PostResponse response = postService.getPostById(1);

        Assertions.assertEquals("jobs", response.getTitle());

    }


}
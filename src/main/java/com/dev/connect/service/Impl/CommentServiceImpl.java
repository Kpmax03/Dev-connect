package com.dev.connect.service.Impl;

import com.dev.connect.RequestDto.CommentRequest;
import com.dev.connect.ResponseDto.CommentResponse;
import com.dev.connect.config.CustomMethods;
import com.dev.connect.entity.Comment;
import com.dev.connect.entity.Post;
import com.dev.connect.entity.User;
import com.dev.connect.exception.IllegalOperationException;
import com.dev.connect.exception.InvalidCradentialException;
import com.dev.connect.exception.ResourceNotFoundException;
import com.dev.connect.repository.CommentRepository;
import com.dev.connect.repository.PostRepository;
import com.dev.connect.repository.UserRepository;
import com.dev.connect.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;
    @Override
    public CommentResponse createComment(int postId, CommentRequest commentRequest, Principal principal) {
        String principalName = principal.getName();
        User selfUser = userRepository.findByEmail(principalName).orElseThrow(() -> new ResourceNotFoundException());
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post not found"));
        Comment comment = Comment.builder()
                .commentId(UUID.randomUUID().toString())
                .content(commentRequest.getContent())
                .commentedAt(LocalDate.now())
                .user(selfUser)
                .post(post)
                .build();
        Comment saveComment = commentRepository.save(comment);

        return CustomMethods.getCommentResponse(saveComment);
    }

    @Override
    public String deleteComment(String commentId, Principal principal) {

        String principalName = principal.getName();

        User selfUser = userRepository.findByEmail(principalName).orElseThrow(() -> new ResourceNotFoundException());

        //only users comment can selfUser delete
        Optional<Comment> comment = selfUser.getComments().stream().filter(singleComment -> {
            return commentId.equals(singleComment.getCommentId());
        }).findFirst();

        //if selfUser wants to delete their others comment from his post
        Comment comment1 = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException());

        User ownerOfComment = comment1.getUser();

        Optional<Comment> optionalComment = selfUser.getPost().stream()
                .flatMap(onePost -> onePost.getCommentList().stream())
                .filter(oneComment -> oneComment.getUser()
                        .equals(ownerOfComment))
                .findFirst();

        if(comment.isEmpty() && optionalComment.isEmpty()){
            throw new InvalidCradentialException("can't delete others comment");
        }else{
           commentRepository.deleteById(commentId);
            return "deleted successfully comment of content : "+comment1.getContent();
        }
    }

    @Override
    public List<CommentResponse> seeCommentOfSpecificPost(int postId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post not found"));

        List<CommentResponse> collect = post.getCommentList().stream().map(single -> {
            return CustomMethods.getCommentResponse(single);
        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public String adminDeleteComment(String commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment id not found"));
        commentRepository.delete(comment);
        return "admin deleted comment of content :"+comment.getContent();
    }
}

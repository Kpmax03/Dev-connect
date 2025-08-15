package com.dev.connect.config;

import com.dev.connect.ResponseDto.*;
import com.dev.connect.apiResponse.PageableResponse;
import com.dev.connect.commonDto.RoleDto;
import com.dev.connect.entity.Comment;
import com.dev.connect.entity.Message;
import com.dev.connect.entity.Post;
import com.dev.connect.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomMethods {

  public static <T,U>PageableResponse<T> getPageableReponse(List<T> collect , Page<U> page){
      return  PageableResponse.<T>builder()
              .content(collect)
              .pageNumber(page.getNumber())
              .pageSize(page.getSize())
              .totalelement(page.getTotalElements())
              .totalPages(page.getTotalPages())
              .isLastPage(page.isLast())
              .build();
  }
  public static UserResponse getUserResponse(User user){
      ModelMapper mapper1=new ModelMapper();

      UserProfileResponse userProfileResponse = mapper1.map(user.getUserProfile(), UserProfileResponse.class);

      List<RoleDto> collect = user.getRole().stream().map(oneRole -> {
          return mapper1.map(oneRole, RoleDto.class);
      }).collect(Collectors.toList());

      List<ShortPostResponse> shortPostResponseList =user.getPost().stream().map(singlePost->{
         return ShortPostResponse.builder().postId(singlePost.getPostId()).title(singlePost.getTitle()).build();
      }).collect(Collectors.toList());

      return UserResponse.builder()
              .id(user.getId())
              .email(user.getEmail())
              .password(user.getPassword())
              .createdAt(user.getCreatedAt())
              .updatedAt(user.getUpdatedAt())
              .userProfileResponse(userProfileResponse)
              .role(collect)
              .shortPostResponseList(shortPostResponseList)
              .build();
  }
  public static CommentResponse getCommentResponse(Comment comment){

      CommentResponse commentResponse=new CommentResponse();
      commentResponse.setCommentId(comment.getCommentId());
      commentResponse.setCommentedAt(comment.getCommentedAt());
      commentResponse.setContent(comment.getContent());
      commentResponse.setUserId(comment.getUser().getId());
      commentResponse.setPostId(comment.getPost().getPostId());
      return commentResponse;
  }
  public static PostResponse getPostResponse(Post post){
      PostResponse postResponse=new PostResponse();
      postResponse.setPostId(post.getPostId());
      postResponse.setType(post.getType());
      postResponse.setTitle(post.getTitle());
      postResponse.setContent(post.getContent());
      postResponse.setCreatedAt(post.getCreatedAt());
      postResponse.setUpdatedAt(post.getUpdatedAt());
      postResponse.setUserId(post.getUser().getId());

      List<Comment> commentList = post.getCommentList();
      List<CommentResponse> collect=new ArrayList<>();
      if(commentList!=null) {
          collect = commentList.stream().map(single -> {
              return getCommentResponse(single);
          }).collect(Collectors.toUnmodifiableList());
      }
      postResponse.setCommentResponseList(collect);

      return  postResponse;

  }
  public static MessageResponse getMessageResponse(Message message){
      return MessageResponse.builder()
              .messageId(message.getMessageId())
              .title(message.getTitle())
              .content(message.getContent())
              .messegedAt(message.getMessagedAt())
              .senderId(message.getSender().getId())
              .receiverId(message.getReceiver().getId())
              .build();
  }
}

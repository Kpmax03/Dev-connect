package com.dev.connect.config;

import com.dev.connect.ResponseDto.*;
import com.dev.connect.apiResponse.PageableResponse;
import com.dev.connect.commonDto.RoleDto;
import com.dev.connect.entity.*;
import com.dev.connect.enums.Domain;
import com.dev.connect.enums.PostType;
import com.dev.connect.enums.Techs;
import com.dev.connect.exception.ResourceNotFoundException;
import com.dev.connect.exception.WantsToGiveException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
      ModelMapper mapper=new ModelMapper();

      UserProfileResponse userProfileResponse = mapper.map(user.getUserProfile(), UserProfileResponse.class);
      userProfileResponse.setDomain(user.getUserProfile().getDomain().toString());
      userProfileResponse.setTechs(user.getUserProfile().getTechs().toString());

      List<RoleDto> collect = user.getRole().stream().map(oneRole -> {
          return mapper.map(oneRole, RoleDto.class);
      }).collect(Collectors.toList());

      List<ShortPostResponse> shortPostResponseList =user.getPost().stream()
              .map(singlePost->{
         return ShortPostResponse.builder()
                 .postId(singlePost.getPostId())
                 .title(singlePost.getTitle())
                 .build();
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
      postResponse.setType(post.getType().toString());
      postResponse.setTitle(post.getTitle());
      postResponse.setContent(post.getContent());
      postResponse.setCreatedAt(post.getCreatedAt());
      postResponse.setUpdatedAt(post.getUpdatedAt());
      postResponse.setUserId(post.getUser().getId());
      postResponse.setTags(post.getTags().toString());
      List<Comment> commentList = post.getCommentList();
      List<CommentResponse> collect=new ArrayList<>();

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

  public static PostType isPostTypeValidOrNot(String type){
      if(type==null && type.isEmpty()){
          throw new ResourceNotFoundException("type cant be empty ");
      }
      type=type.toUpperCase();
      try {

      PostType.valueOf(type);

      }catch (IllegalArgumentException ex){
          throw new WantsToGiveException("type "+type+" is wrong ");
      }
      return PostType.valueOf(type);
  }

  public static Set<Techs> isTechsValidOrNot(Set<String> techs){

      if(techs!=null && !techs.isEmpty()){

          Set<Techs> stringSet = techs.stream().map(one -> {
              try {
                  Techs.valueOf(one.toUpperCase());
              } catch (IllegalArgumentException ex) {
                  throw new WantsToGiveException(" tech " + one + " not support and valid techs are plz visit /help/techs for know more");
              }
              return Techs.valueOf(one.toUpperCase());
          }).collect(Collectors.toSet());

          return stringSet;
      }
      return null;
  }

  public static Set<Domain> isDomainValidOrNot(Set<String> domain){

      if(domain!=null && !domain.isEmpty()){

          Set<Domain> collect = domain.stream().map(oneDomain -> {
              try {
                  Domain.valueOf(oneDomain);
              } catch (IllegalArgumentException ex) {
                  throw new WantsToGiveException(" Domain " + oneDomain + " not support and valid domain are plz visit /help/domains for know more");
              }
              return Domain.valueOf(oneDomain);
          }).collect(Collectors.toSet());
          return collect;
      }
      return null;
  }

  public static Set<String> convertTagsToLowerCase(Set<String> tags){
      return tags.stream().map(oneTag->oneTag.toLowerCase()).collect(Collectors.toSet());
  }

}

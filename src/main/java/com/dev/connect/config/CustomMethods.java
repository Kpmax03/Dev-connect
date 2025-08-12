package com.dev.connect.config;

import com.dev.connect.ResponseDto.ShortPost;
import com.dev.connect.ResponseDto.UserProfileResponse;
import com.dev.connect.ResponseDto.UserResponse;
import com.dev.connect.apiResponse.PageableResponse;
import com.dev.connect.commonDto.RoleDto;
import com.dev.connect.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

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

      List<ShortPost> shortPostList=user.getPost().stream().map(singlePost->{
         return ShortPost.builder().postId(singlePost.getPostId()).title(singlePost.getTitle()).build();
      }).collect(Collectors.toList());

      return UserResponse.builder()
              .id(user.getId())
              .email(user.getEmail())
              .password(user.getPassword())
              .createdAt(user.getCreatedAt())
              .updatedAt(user.getUpdatedAt())
              .userProfileResponse(userProfileResponse)
              .role(collect)
              .shortPostList(shortPostList)
              .build();
  }
}

package com.dev.connect.config;

import com.dev.connect.ApiResponse.PageableResponse;
import org.springframework.data.domain.Page;

import java.util.List;

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
}

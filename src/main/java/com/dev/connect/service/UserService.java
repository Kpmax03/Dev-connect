package com.dev.connect.service;

import com.dev.connect.ApiResponse.PageableResponse;
import com.dev.connect.RequestDto.UserRequest;
import com.dev.connect.ResponseDto.UserResponse;

public interface UserService {
    public UserResponse registerUser(UserRequest userRequestDto);
    public UserResponse updateUser(String id, UserRequest userRequestDto);
    public String deleteUser(String id);
    public UserResponse getById(String id);
    public PageableResponse<UserResponse> getAll(int pageNumber, int pageSize, String sortBy);
}

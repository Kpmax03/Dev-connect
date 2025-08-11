package com.dev.connect.service;

import com.dev.connect.ApiResponse.PageableResponse;
import com.dev.connect.RequestDto.UserRequest;
import com.dev.connect.ResponseDto.UserResponse;

import java.security.Principal;

public interface UserService {
    public UserResponse registerUser(UserRequest userRequestDto);
    public UserResponse updateUser( UserRequest userRequestDto, Principal principal);
    public String deleteUser(Principal principal);
    public UserResponse getById(String id);
    public PageableResponse<UserResponse> getAll(int pageNumber, int pageSize, String sortBy);

    public UserResponse adminUpdateUser( String userId,UserRequest userRequestDto);
    public String adminDeleteUser(String userId);
}

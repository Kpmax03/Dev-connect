package com.dev.connect.service;

import com.dev.connect.ApiResponse.PageableResponse;
import com.dev.connect.dto.UserDto;

public interface UserService {
    public UserDto registerUser(UserDto userDto);
    public UserDto updateUser(String id, UserDto userDto);
    public String deleteUser(String id);
    public UserDto getById(String id);
    public PageableResponse<UserDto> getAll(int pageNumber, int pageSize, String sortBy);

}

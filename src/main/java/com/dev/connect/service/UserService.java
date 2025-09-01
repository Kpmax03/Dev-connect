package com.dev.connect.service;

import com.dev.connect.apiResponse.PageableResponse;
import com.dev.connect.RequestDto.UserRequest;
import com.dev.connect.ResponseDto.UserResponse;

import java.security.Principal;
import java.util.List;

public interface UserService {

    public UserResponse updateUser( UserRequest userRequestDto, Principal principal);
    public String deleteUser(Principal principal);
    public UserResponse getById(String id);
    public PageableResponse<UserResponse> getAll(int pageNumber, int pageSize, String sortBy);

    // for searching
    public List<UserResponse> searchUserByDomain(String domain);
    public List<UserResponse> searchUserByTechs(List<String> techs);
    public List<UserResponse> searchUserByDomainAndTechs(String domain,List<String> techs);

    // for admin task only
    public UserResponse adminUpdateUser( String userId,UserRequest userRequestDto);
    public String adminDeleteUser(String userId);
}

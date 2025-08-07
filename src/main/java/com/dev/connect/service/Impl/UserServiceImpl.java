package com.dev.connect.service.Impl;

import com.dev.connect.ApiResponse.PageableResponse;
import com.dev.connect.RequestDto.UserProfileRequest;
import com.dev.connect.RequestDto.UserRequest;
import com.dev.connect.ResponseDto.ShortPost;
import com.dev.connect.ResponseDto.UserProfileResponse;
import com.dev.connect.ResponseDto.UserResponse;
import com.dev.connect.config.CustomMethods;
import com.dev.connect.entity.Post;
import com.dev.connect.entity.User;
import com.dev.connect.entity.UserProfile;
import com.dev.connect.exception.ResourceNotFoundException;
import com.dev.connect.repository.UserRepository;
import com.dev.connect.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Override
    public UserResponse registerUser(UserRequest userRequest) {
        UserProfileRequest userProfileRequestDto = userRequest.getUserProfileRequestDto();
        User user = mapper.map(userRequest, User.class);
        UserProfile userProfile=mapper.map(userRequest.getUserProfileRequestDto(),UserProfile.class);

        user.setId(UUID.randomUUID().toString());
        user.setCreatedAt(LocalDate.now());
        user.setUpdatedAt(LocalDate.now());

        user.setUserProfile(userProfile);
        userProfile.setUser(user);

        User save = userRepository.save(user);

        UserResponse userResponse = mapper.map(save, UserResponse.class);
        UserProfileResponse userProfileResponse = mapper.map(save.getUserProfile(), UserProfileResponse.class);

        userResponse.setUserProfileResponseDto(userProfileResponse);
        return userResponse;
    }

    @Override
    public UserResponse updateUser(String userId, UserRequest userRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException());
        UserProfileRequest userProfileRequestDto = userRequest.getUserProfileRequestDto();
        UserProfile userProfile = mapper.map(userProfileRequestDto, UserProfile.class);

        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setRole(userRequest.getRole());
        user.setUpdatedAt(LocalDate.now());
        user.setUserProfile(userProfile);
        userProfile.setUser(user);

        User save = userRepository.save(user);
        UserProfileResponse userProfileResponse = mapper.map(save.getUserProfile(), UserProfileResponse.class);

        UserResponse userResponse = mapper.map(save, UserResponse.class);
        userResponse.setUserProfileResponseDto(userProfileResponse);

        List<ShortPost> collect = user.getPost().stream().map(singlePost -> {
            return ShortPost.builder().postId(singlePost.getPostId()).title(singlePost.getTitle()).build();
        }).collect(Collectors.toUnmodifiableList());

        userResponse.setPosts(collect);

        return userResponse;
    }

    @Override
    public String deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        userRepository.delete(user);
     return "deleted user of id = "+id;
    }

    @Override
    public UserResponse getById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());

        UserProfile userProfile = user.getUserProfile();

        UserProfileResponse userProfileResponse = mapper.map(userProfile, UserProfileResponse.class);
        UserResponse userResponse = mapper.map(user, UserResponse.class);

        userResponse.setUserProfileResponseDto(userProfileResponse);

        List<ShortPost> collect = user.getPost().stream().map(singlePost -> {
            return ShortPost.builder().postId(singlePost.getPostId()).title(singlePost.getTitle()).build();
        }).collect(Collectors.toUnmodifiableList());

        userResponse.setPosts(collect);

        return userResponse;
    }

    @Override
    public PageableResponse<UserResponse> getAll(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable= PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));

        Page<User> page = userRepository.findAll(pageable);

        List<UserResponse> collect = page.stream().map(oneUser -> {
            UserProfile userProfile = oneUser.getUserProfile();

            UserResponse userResponse = mapper.map(oneUser, UserResponse.class);
            UserProfileResponse userProfileResponse = mapper.map(userProfile, UserProfileResponse.class);

            userResponse.setUserProfileResponseDto(userProfileResponse);
        List<ShortPost> shortPostList = oneUser.getPost().stream().map(singlePost -> {
            return ShortPost.builder().postId(singlePost.getPostId()).title(singlePost.getTitle()).build();
        }).collect(Collectors.toUnmodifiableList());

        userResponse.setPosts(shortPostList);
            return userResponse;
        }).collect(Collectors.toList());

        PageableResponse<UserResponse> pageableResponse = CustomMethods.getPageableReponse(collect,page);

        return pageableResponse;
    }
}

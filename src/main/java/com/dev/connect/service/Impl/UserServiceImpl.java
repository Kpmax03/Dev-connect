package com.dev.connect.service.Impl;

import com.dev.connect.ApiResponse.PageableResponse;
import com.dev.connect.RequestDto.UserProfileRequest;
import com.dev.connect.RequestDto.UserRequest;
import com.dev.connect.ResponseDto.PostResponse;
import com.dev.connect.ResponseDto.ShortPost;
import com.dev.connect.ResponseDto.UserProfileResponse;
import com.dev.connect.ResponseDto.UserResponse;
import com.dev.connect.config.CustomMethods;
import com.dev.connect.dto.RoleDto;
import com.dev.connect.entity.Post;
import com.dev.connect.entity.Role;
import com.dev.connect.entity.User;
import com.dev.connect.entity.UserProfile;
import com.dev.connect.exception.ResourceNotFoundException;
import com.dev.connect.repository.RoleRepository;
import com.dev.connect.repository.UserRepository;
import com.dev.connect.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserResponse registerUser(UserRequest userRequest) {
        String password = userRequest.getPassword();
        User user = mapper.map(userRequest, User.class);
        UserProfile userProfile=mapper.map(userRequest.getUserProfileRequestDto(),UserProfile.class);
        List<Integer> collect = userRequest.getRoleDtoList().stream().map(singleRole -> {
            return singleRole.getRoleId();
        }).collect(Collectors.toList());

        List<Role> roleList = roleRepository.findAllById(collect);

        user.setPassword(passwordEncoder.encode(password));
        user.setId(UUID.randomUUID().toString());
        user.setCreatedAt(LocalDate.now());
        user.setUpdatedAt(LocalDate.now());
        user.setRole(roleList);

        user.setUserProfile(userProfile);
        userProfile.setUser(user);

        User save = userRepository.save(user);

        UserResponse userResponse = mapper.map(save, UserResponse.class);
        UserProfileResponse userProfileResponse = mapper.map(save.getUserProfile(), UserProfileResponse.class);

        userResponse.setUserProfileResponseDto(userProfileResponse);
        return userResponse;
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest,Principal principal) {
        String name = principal.getName();
        User user = userRepository.findByEmail(name).orElseThrow(() -> new ResourceNotFoundException());

            UserProfileRequest userProfileRequestDto = userRequest.getUserProfileRequestDto();
            UserProfile userProfile = mapper.map(userProfileRequestDto, UserProfile.class);
            List<RoleDto> roleDtoList = userRequest.getRoleDtoList();
            List<Role> roleList = roleDtoList.stream().map(oneRole -> {
                return mapper.map(oneRole, Role.class);
            }).collect(Collectors.toList());

            user.setEmail(userRequest.getEmail());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            user.setRole(roleList);
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
    public String deleteUser(Principal principal) {
        String name = principal.getName();
        User user = userRepository.findByEmail(name).orElseThrow(() -> new ResourceNotFoundException("cant find username "));
        userRepository.delete(user);
     return "deleted user of username = "+name;
    }

    @Override
    public UserResponse getById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("cant find id"));

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

    @Override
    public UserResponse adminUpdateUser(String userId, UserRequest userRequestDto) {
        UserProfileRequest userProfileRequestDto = userRequestDto.getUserProfileRequestDto();
        UserProfile userProfile = mapper.map(userProfileRequestDto, UserProfile.class);
        List<Role> collect = userRequestDto.getRoleDtoList().stream().map(oneRole -> {
            return mapper.map(oneRole, Role.class);
        }).collect(Collectors.toList());

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("id not found "));

        user.setUpdatedAt(LocalDate.now());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());

        user.setUserProfile(userProfile);
        user.setRole(collect);

        User save = userRepository.save(user);

        UserProfile userProfile1 = user.getUserProfile();
        List<Role> roleList = user.getRole();
        List<Post> postList = user.getPost();

        UserResponse userResponse = mapper.map(save, UserResponse.class);
        UserProfileResponse userProfileResponse = mapper.map(userProfile1, UserProfileResponse.class);

        List<RoleDto> roleDtoList= roleList.stream().map(singleRole->{
            return mapper.map(singleRole, RoleDto.class);
        }).collect(Collectors.toUnmodifiableList());

        List<ShortPost> shortPostList= postList.stream().map(singlePost->{
            return ShortPost.builder().postId(singlePost.getPostId()).title(singlePost.getTitle()).build();
        }).collect(Collectors.toList());

        userResponse.setUserProfileResponseDto(userProfileResponse);
        userResponse.setRole(roleDtoList);
        userResponse.setPosts(shortPostList);

        return userResponse;
    }

    @Override
    public String adminDeleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("id not found"));
         userRepository.delete(user);
        return "deleted id from admin request id="+userId;
    }
}

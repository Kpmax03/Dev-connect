package com.dev.connect.service.Impl;

import com.dev.connect.apiResponse.PageableResponse;
import com.dev.connect.RequestDto.UserProfileRequest;
import com.dev.connect.RequestDto.UserRequest;
import com.dev.connect.ResponseDto.UserResponse;
import com.dev.connect.config.CustomMethods;
import com.dev.connect.commonDto.RoleDto;
import com.dev.connect.entity.*;
import com.dev.connect.exception.ResourceNotFoundException;
import com.dev.connect.repository.ConnectionRepository;
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

    @Autowired
    private ConnectionRepository connectionRepository;



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

        Long countByFollower = connectionRepository.countByFollower(save);
        Long coutByFollowing = connectionRepository.countByFollowing(save);

            UserResponse userResponse =CustomMethods.getUserResponse(save);

            userResponse.setFollower(coutByFollowing);
            userResponse.setFollowing(countByFollower);

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

        UserResponse userResponse = CustomMethods.getUserResponse(user);

        Long countByFollower = connectionRepository.countByFollower(user);
        Long countByFollowing = connectionRepository.countByFollowing(user);

        userResponse.setFollower(countByFollowing);
        userResponse.setFollowing(countByFollower);

        return userResponse;

    }

    @Override
    public PageableResponse<UserResponse> getAll(int pageNumber, int pageSize, String sortBy) {

        Pageable pageable= PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));

        Page<User> page = userRepository.findAll(pageable);

        List<UserResponse> collect = page.stream().map(oneUser -> {

            UserResponse userResponse = CustomMethods.getUserResponse(oneUser);

            Long countByFollower = connectionRepository.countByFollower(oneUser);
            Long countByFollowing = connectionRepository.countByFollowing(oneUser);

            userResponse.setFollower(countByFollowing);
            userResponse.setFollowing(countByFollower);

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

        UserResponse userResponse = CustomMethods.getUserResponse(save);

        userResponse.setFollower(connectionRepository.countByFollowing(save));
        userResponse.setFollowing(connectionRepository.countByFollower(save));

        return userResponse;
    }

    @Override
    public String adminDeleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("id not found"));
         userRepository.delete(user);
        return "deleted id from admin request id="+userId;
    }
}

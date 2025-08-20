package com.dev.connect.service.impl;

import com.dev.connect.RequestDto.UserProfileRequest;
import com.dev.connect.RequestDto.UserRequest;
import com.dev.connect.ResponseDto.UserResponse;
import com.dev.connect.commonDto.RoleDto;
import com.dev.connect.entity.Role;
import com.dev.connect.entity.User;
import com.dev.connect.entity.UserProfile;
import com.dev.connect.repository.ConnectionRepository;
import com.dev.connect.repository.RoleRepository;
import com.dev.connect.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    private ModelMapper mapper=new ModelMapper();

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ConnectionRepository connectionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
     void init(){
         authService=new AuthServiceImpl(mapper,roleRepository,connectionRepository,userRepository,passwordEncoder);
     }

    @Test
    void registerUser() {

        UserRequest userRequest = UserRequest.builder().email("kp5911800@gmail").password("kisddfggff").roleDtoList(List.of(RoleDto.builder().roleId(1).name("ADMIN").build())).userProfileRequestDto(UserProfileRequest.builder().firstName("kishan").lastName("prajapati").bio("i am tsting").age(19).gender("male").build()).build();

        List<Role> role = List.of(Role.builder().roleId(1).name("ROLE_ADMIN").build());
        User user = User.builder().email("kp5911800@gmail.com").userProfile(new UserProfile()).role(role).post(new ArrayList<>()).build();


        Mockito.when(roleRepository.findAllById(Mockito.any())).thenReturn(role);
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encoded");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(connectionRepository.countByFollower(Mockito.any(User.class))).thenReturn(123456L);
        Mockito.when(connectionRepository.countByFollowing(Mockito.any(User.class))).thenReturn(654321L);
        UserResponse userResponse = authService.registerUser(userRequest);
        Assertions.assertNotNull(userResponse);
        System.out.println(userResponse.getEmail());
        System.out.println("follower"+userResponse.getFollower());
        System.out.println("following"+userResponse.getFollowing());

    }

    @Test
    @Disabled
    void login() {

    }
    @Disabled
    @Test
    void getCurrentUser() {
    }
}
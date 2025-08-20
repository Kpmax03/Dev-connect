package com.dev.connect.service.impl;

import com.dev.connect.RequestDto.UserProfileRequest;
import com.dev.connect.RequestDto.UserRequest;
import com.dev.connect.ResponseDto.UserResponse;
import com.dev.connect.apiResponse.PageableResponse;
import com.dev.connect.commonDto.RoleDto;
import com.dev.connect.entity.User;
import com.dev.connect.entity.UserProfile;
import com.dev.connect.repository.ConnectionRepository;
import com.dev.connect.repository.RoleRepository;
import com.dev.connect.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private final ModelMapper mapper=new ModelMapper();

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ConnectionRepository connectionRepository;

    @BeforeEach
    void init(){
        userService=new UserServiceImpl(userRepository,passwordEncoder,mapper,roleRepository,connectionRepository);
    }
    @Test
    void updateUser() {

        Principal principal=()->"kp5911800@gmail.com";
        UserRequest userRequest = UserRequest.builder().email("kp5911800@gmail.com").password("qazxwefghjhgf").userProfileRequestDto(new UserProfileRequest()).roleDtoList(new ArrayList<>()).build();
        User user = User.builder().email("kp5911800@gmail.com").password("qazxwefghjhgf").userProfile(new UserProfile()).role(new ArrayList<>()).post(new ArrayList<>()).build();

        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("12");

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        Mockito.when(connectionRepository.countByFollower(Mockito.any(User.class))).thenReturn(123L);
        Mockito.when(connectionRepository.countByFollowing(Mockito.any(User.class))).thenReturn(321L);

        UserResponse userResponse = userService.updateUser(userRequest,principal);

        assertNotNull(userResponse);
        assertEquals(321,userResponse.getFollower());
        assertEquals(123,userResponse.getFollowing());

    }


    @Test
    void getById() {
        User user = User.builder().email("Kp5911800@gmail.com").password("kjhgfdsa").role(new ArrayList<>()).userProfile(new UserProfile()).post(new ArrayList<>()).build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));

        Mockito.when(connectionRepository.countByFollower(Mockito.any(User.class))).thenReturn(123L);
        Mockito.when(connectionRepository.countByFollowing(Mockito.any(User.class))).thenReturn(321L);

        UserResponse actual = userService.getById("kjhg");
        assertNotNull(actual);
        assertEquals(user.getEmail(),actual.getEmail());
        assertEquals(123,actual.getFollowing());
        assertEquals(321,actual.getFollower());
        System.out.println(actual);

    }

    @Test
    void getAll() {
        User user = User.builder().email("Kp5911800@gmail.com").password("12").post(new ArrayList<>()).userProfile(new UserProfile()).role(new ArrayList<>()).build();
        User user2 = User.builder().email("Kp591180@gmail.com").password("12").post(new ArrayList<>()).userProfile(new UserProfile()).role(new ArrayList<>()).build();
        User user3 = User.builder().email("Kp59118@gmail.com").password("12").post(new ArrayList<>()).userProfile(new UserProfile()).role(new ArrayList<>()).build();
        ArrayList<User> list=new ArrayList<>();
        list.add(user);list.add(user2);list.add(user3);
        Page<User> page=new PageImpl<>(list);

        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

        Mockito.when(connectionRepository.countByFollower(Mockito.any(User.class))).thenReturn(123L);
        Mockito.when(connectionRepository.countByFollowing(Mockito.any(User.class))).thenReturn(321L);

        PageableResponse<UserResponse> actualresult = userService.getAll(0, 10, "email");

        assertEquals(page.getTotalElements(),actualresult.getTotalelement());
        assertEquals(page.getNumber(),actualresult.getPageNumber());
        assertEquals(page.getTotalPages(),actualresult.getTotalPages());
        assertEquals(page.isLast(),actualresult.isLastPage());
     //   assertNotEquals(list,actualresult.getContent());
    }

    @Test
    void adminUpdateUser() {
        UserRequest userRequest = UserRequest.builder().email("kp5911800@gmail.com").password("12").roleDtoList(List.of(RoleDto.builder().roleId(1).name("ADMIN").build())).userProfileRequestDto(UserProfileRequest.builder().firstName("kishan").lastName("prajapati").age(19).bio("i am testing user service impl").gender("male").build()).build();
        User user = User.builder().email("Kp5911800@gmail.com").password("12").post(new ArrayList<>()).userProfile(new UserProfile()).role(new ArrayList<>()).build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user)).thenReturn(user);

        Mockito.when(connectionRepository.countByFollower(Mockito.any(User.class))).thenReturn(123L);
        Mockito.when(connectionRepository.countByFollowing(Mockito.any(User.class))).thenReturn(321L);

        UserResponse actual = userService.adminUpdateUser("wdf", userRequest);

        assertNotNull(actual);

       assertEquals(123,actual.getFollowing());
        assertEquals(321,actual.getFollower());
        System.out.println(actual);
    }

}
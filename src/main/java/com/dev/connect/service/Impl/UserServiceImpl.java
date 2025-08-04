package com.dev.connect.service.Impl;

import com.dev.connect.ApiResponse.PageableResponse;
import com.dev.connect.dto.UserDto;
import com.dev.connect.entity.User;
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
    UserRepository userRepository;

    @Autowired
    ModelMapper mapper;

//    @Autowired
//    PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerUser(UserDto userDto) {
        userDto.setId(UUID.randomUUID().toString());
        userDto.setFollowers(0);
        userDto.setFollowing(0);
        userDto.setCreatedAt(LocalDate.now());
        //String password = userDto.getPassword();
        //userDto.setPassword(passwordEncoder.encode(password));
        User map = mapper.map(userDto, User.class);
        return mapper.map(userRepository.save(map),UserDto.class);
    }

    @Override
    public UserDto updateUser(String id, UserDto userDto) {
        User byId = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("id not found ."));
        byId.setFirstName(userDto.getFirstName());
        byId.setLastName(userDto.getLastName());
        byId.setAge(userDto.getAge());
        byId.setEmail(userDto.getEmail());
        //byId.setPassword(passwordEncoder.encode(userDto.getPassword()));
      /**/  byId.setPassword(userDto.getPassword());
        byId.setRole(userDto.getRole());
        byId.setBio(userDto.getBio());
        User updated = userRepository.save(byId);
        return mapper.map(updated,UserDto.class);
    }

    @Override
    public String deleteUser(String id) {
        userRepository.deleteById(id);
        return "deleted User of id : "+id;
    }

    @Override
    public UserDto getById(String id) {
        return mapper.map(userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("user id not found")),UserDto.class);
    }

    @Override
    public PageableResponse<UserDto> getAll(int pageNumber, int pageSize, String sortBy) {
        Sort sort= Sort.by(sortBy);
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<User> all = userRepository.findAll(pageable);
        List<User> allUser=all.getContent();
        List<UserDto> collect = allUser.stream().map(oneuser -> {
            return mapper.map(oneuser, UserDto.class);
        }).collect(Collectors.toList());
        PageableResponse response=PageableResponse.<UserDto>builder()
                .content(collect)
                .pageNumber(all.getNumber())
                .pageSize(all.getSize())
                .totalelement(all.getTotalElements())
                .totalPages(all.getTotalPages())
                .build();
        return response;
    }
}


package com.dev.connect.service.impl;

import com.dev.connect.RequestDto.JwtRequest;
import com.dev.connect.RequestDto.UserRequest;
import com.dev.connect.ResponseDto.JwtResponse;
import com.dev.connect.ResponseDto.UserResponse;
import com.dev.connect.config.CustomMethods;
import com.dev.connect.entity.Role;
import com.dev.connect.entity.User;
import com.dev.connect.entity.UserProfile;
import com.dev.connect.enums.Domain;
import com.dev.connect.enums.Techs;
import com.dev.connect.jwt.JwtUtil;
import com.dev.connect.repository.ConnectionRepository;
import com.dev.connect.repository.RoleRepository;
import com.dev.connect.repository.UserRepository;
import com.dev.connect.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service@AllArgsConstructor@NoArgsConstructor
public class AuthServiceImpl implements AuthService {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserResponse registerUser(UserRequest userRequest) {

        String password = userRequest.getPassword();
        Set<Techs> validTechs = CustomMethods.isTechsValidOrNot(userRequest.getUserProfileRequestDto().getTechs());
        Set<Domain> validDomains = CustomMethods.isDomainValidOrNot(userRequest.getUserProfileRequestDto().getDomain());

        User user = mapper.map(userRequest, User.class);
        UserProfile userProfile=mapper.map(userRequest.getUserProfileRequestDto(),UserProfile.class);

        userProfile.setTechs(validTechs);
        userProfile.setDomain(validDomains);

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

        Long coutByFollow = connectionRepository.countByFollower(save);
        Long coutByFollowing = connectionRepository.countByFollowing(save);
        UserResponse userResponse= CustomMethods.getUserResponse(save);

        userResponse.setFollower(coutByFollowing);
        userResponse.setFollowing(coutByFollow);

        return userResponse;
    }

    @Override
    public JwtResponse login(JwtRequest jwtRequest) {

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String token = jwtUtil.generateToken(jwtRequest.getUserName());
        return new JwtResponse(token);

    }


    @Override
    public User getCurrentUser(Principal principal) {
        String principalName = principal.getName();
        Optional<User> user = userRepository.findByEmail(principalName);
        return user.get();
    }



























    // for testing bcoz of this parameter
    public AuthServiceImpl(ModelMapper mapper, RoleRepository roleRepository, ConnectionRepository connectionRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.mapper = mapper;
        this.roleRepository = roleRepository;
        this.connectionRepository = connectionRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
}

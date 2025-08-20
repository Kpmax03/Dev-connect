package com.dev.connect.service.impl;

import com.dev.connect.entity.Connection;
import com.dev.connect.entity.User;
import com.dev.connect.exception.IllegalOperationException;
import com.dev.connect.exception.ResourceNotFoundException;
import com.dev.connect.repository.ConnectionRepository;
import com.dev.connect.repository.UserRepository;
import com.dev.connect.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Override
    public String follow(String followingId, Principal principal) {

        String principalName = principal.getName();
        Optional<User> optional = userRepository.findByEmail(principalName);
        User selfUser = optional.get();

        User followingUser = userRepository.findById(followingId).orElseThrow(() -> new ResourceNotFoundException("following id not exist"));

        if(followingUser.getId().equals(selfUser.getId())){
            throw new IllegalOperationException("can't follow to self");
        }
        Optional<Connection> findedUser = selfUser.getFollowingList().stream().filter(singleConnection ->
                singleConnection
                        .getFollowing()
                        .getId()
                        .equals(followingUser.getId()))
                .findFirst();
        if(findedUser.isEmpty()){
            Connection connection=new Connection();
            connection.setConnectionId(UUID.randomUUID().toString());
            connection.setFollower(selfUser);
            connection.setFollowing(followingUser);
            connectionRepository.save(connection);
            return principalName+" follows to "+followingUser.getEmail();
        }else
            return "already following " + principalName + " to " + followingUser.getEmail();
    }

    @Override
    public String unFollow(String unFollowingId, Principal principal) {
        //selfuser
        String principalName = principal.getName();
        Optional<User> optional = userRepository.findByEmail(principalName);
        User selfUser = optional.get();
        //targetuser
        User unFollowingUser = userRepository.findById(unFollowingId).orElseThrow(() -> new ResourceNotFoundException("following id not exist"));

        if (selfUser.getEmail().equals(unFollowingUser.getEmail())) {
            throw new IllegalOperationException("can't unfollow to self");
        }
        Optional<Connection> findedUser = selfUser.getFollowingList().stream().filter(singleConnection ->
                        singleConnection
                                .getFollowing()
                                .getId()
                                .equals(unFollowingUser.getId()))
                .findFirst();

        if (!findedUser.isEmpty()) {
            connectionRepository.delete(findedUser.get());
            return "unfollowing " + principalName + " to " + unFollowingUser.getEmail();
        } else {
            return "already not following " + principalName + " to " + unFollowingUser.getEmail();
        }
    }

    @Override
    public Long getFollower(String userId, Principal principal) {

        String principalName = principal.getName();
        User user;

        if(userId.isEmpty()){
           user= userRepository.findByEmail(principalName).orElseThrow(()->new ResourceNotFoundException());
        }else{
            user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("userid not found"));
        }
        Long followCount = connectionRepository.countByFollowing(user);

        return followCount;
    }

    @Override
    public Long getFollowing(String userId, Principal principal) {
        String principalName = principal.getName();
        User user;

        if(userId.isEmpty()){
            user= userRepository.findByEmail(principalName).orElseThrow(()->new ResourceNotFoundException("userid not found"));
        }else{
            user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("userid not found"));
        }

        Long followCount = connectionRepository.countByFollower(user);

        return followCount;
    }
}

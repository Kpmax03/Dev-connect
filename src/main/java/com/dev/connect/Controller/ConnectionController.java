package com.dev.connect.Controller;

import com.dev.connect.ResponseDto.ConnectionResponse;
import com.dev.connect.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class ConnectionController {
    @Autowired
    private ConnectionService connectionService;

    @PostMapping("/follow/{followingId}")
    public ResponseEntity<String> follow(@PathVariable String followingId, Principal principal){
        return new ResponseEntity<>(connectionService.follow(followingId,principal), HttpStatus.OK);
    }

    @PostMapping("/unfollow/{unFollowingId}")
    public ResponseEntity<String> unFollow(@PathVariable String unFollowingId, Principal principal){
        return new ResponseEntity<>(connectionService.unFollow(unFollowingId,principal),HttpStatus.OK);
    }

    @GetMapping("/followers")
    public ResponseEntity<Long> getFollowersOfLoggedUser(Principal principal){
        return new ResponseEntity<>(connectionService.getFollower("",principal),HttpStatus.OK);
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<Long> getFollowers(@PathVariable(required = false) String userId,Principal principal){
        return new ResponseEntity<>(connectionService.getFollower(userId,principal),HttpStatus.OK);
    }

    @GetMapping("/following")
    public ResponseEntity<Long> getFollowingOfLoggedUser(Principal principal){
        return new ResponseEntity<>(connectionService.getFollowing("",principal),HttpStatus.OK);
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<Long> getFollowing(@PathVariable(required = false) String userId,Principal principal){
        return new ResponseEntity<>(connectionService.getFollowing(userId,principal),HttpStatus.OK);
    }


}

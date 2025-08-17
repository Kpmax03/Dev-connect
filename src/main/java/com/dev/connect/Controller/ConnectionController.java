package com.dev.connect.Controller;

import com.dev.connect.service.ConnectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Tag(name = "connection --(follow,unfollow)")
public class ConnectionController {
    @Autowired
    private ConnectionService connectionService;

    @Operation(summary = "for follow")
    @PostMapping("/follow/{followingId}")
    public ResponseEntity<String> follow(@PathVariable String followingId, Principal principal){
        return new ResponseEntity<>(connectionService.follow(followingId,principal), HttpStatus.OK);
    }

    @Operation(summary = "for unfollow")
    @PostMapping("/unfollow/{unFollowingId}")
    public ResponseEntity<String> unFollow(@PathVariable String unFollowingId, Principal principal){
        return new ResponseEntity<>(connectionService.unFollow(unFollowingId,principal),HttpStatus.OK);
    }

    @Operation(summary = "get all followers of current user")
    @GetMapping("/followers")
    public ResponseEntity<Long> getFollowersOfLoggedUser(Principal principal){
        return new ResponseEntity<>(connectionService.getFollower("",principal),HttpStatus.OK);
    }

    @Operation(summary = "get followers of any user")
    @GetMapping("/followers/{userId}")
    public ResponseEntity<Long> getFollowers(@PathVariable(required = false) String userId,Principal principal){
        return new ResponseEntity<>(connectionService.getFollower(userId,principal),HttpStatus.OK);
    }

    @Operation(summary = "get following of current user")
    @GetMapping("/following")
    public ResponseEntity<Long> getFollowingOfLoggedUser(Principal principal){
        return new ResponseEntity<>(connectionService.getFollowing("",principal),HttpStatus.OK);
    }

    @Operation(summary = "get following of any user")
    @GetMapping("/following/{userId}")
    public ResponseEntity<Long> getFollowing(@PathVariable(required = false) String userId,Principal principal){
        return new ResponseEntity<>(connectionService.getFollowing(userId,principal),HttpStatus.OK);
    }

}

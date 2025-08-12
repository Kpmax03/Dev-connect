package com.dev.connect.service;

import java.security.Principal;

public interface ConnectionService {
    public String follow(String followingId, Principal principal);
    public String unFollow(String unFollowingId,Principal principal);
    public Long getFollower(String userId,Principal principal);
    public Long getFollowing(String userId,Principal principal);
}

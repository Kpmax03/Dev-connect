package com.dev.connect.repository;

import com.dev.connect.entity.Connection;
import com.dev.connect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection,String> {
    Long countByFollower(User follow);
    Long countByFollowing(User following);
}

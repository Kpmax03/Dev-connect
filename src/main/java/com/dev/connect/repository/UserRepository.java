package com.dev.connect.repository;

import com.dev.connect.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
     Optional<User> findByEmail(String email);
}

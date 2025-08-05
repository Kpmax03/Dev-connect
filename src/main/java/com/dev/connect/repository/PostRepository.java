package com.dev.connect.repository;

import com.dev.connect.entity.Post;
import com.dev.connect.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    Page<Post> findByUser(User user, Pageable pageable);
}

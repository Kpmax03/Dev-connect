package com.dev.connect.repository;

import com.dev.connect.entity.Comment;
import com.dev.connect.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,String> {
    Optional<Long> countByPost(Post post);
}

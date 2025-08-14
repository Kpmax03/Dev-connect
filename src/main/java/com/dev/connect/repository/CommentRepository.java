package com.dev.connect.repository;

import com.dev.connect.entity.Comment;
import com.dev.connect.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,String> {
    Optional<Long> countByUser(User user);
}

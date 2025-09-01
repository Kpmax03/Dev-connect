package com.dev.connect.repository;

import com.dev.connect.entity.Post;
import com.dev.connect.enums.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    @Query("select p from Post p where p.user.id = :userId ")
    Page<Post> findAllPostByUser(@Param("userId") String userId, Pageable pageable);

    List<Post> findByTagsIn(List<String> listOfTags);
    List<Post> findByType(PostType postType);
    List<Post> findByTypeAndTagsIn(PostType postType,List<String> listOfTags);
}

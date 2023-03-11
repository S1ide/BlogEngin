package com.naumen.blogeng.repository;

import com.naumen.blogeng.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post getPostById(long id);
}

package com.naumen.blogeng.repository;

import com.naumen.blogeng.model.Comment;
import com.naumen.blogeng.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository <Comment, Long> {
    Comment getCommentById(long id);


}

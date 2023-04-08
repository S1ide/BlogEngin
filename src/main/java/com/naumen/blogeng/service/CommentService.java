package com.naumen.blogeng.service;

import com.naumen.blogeng.model.User;
import com.naumen.blogeng.model.Comment;
import com.naumen.blogeng.repository.CommentRepository;
import com.naumen.blogeng.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public void addComment(String text, String postId, User user) {
        Comment comment = new Comment(text, postRepository.getPostById(Long.parseLong(postId)), user);
        commentRepository.save(comment);
}

    public Comment getById(long id) {
        return commentRepository.getCommentById(id);
    }

    public void updateComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void removeComment(Comment comment) {
        commentRepository.delete(comment);
    }
}

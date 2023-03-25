package com.naumen.blogeng.service;

import com.naumen.blogeng.model.BlogUser;
import com.naumen.blogeng.model.Comment;
import com.naumen.blogeng.model.Post;
import com.naumen.blogeng.repository.CommentRepository;
import com.naumen.blogeng.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public void addComment(String text, String postId, BlogUser blogUser) {
        Comment comment = new Comment(text, postRepository.getPostById(Long.parseLong(postId)), blogUser);
        commentRepository.save(comment);
}

    public Comment getById(long id) {
        return commentRepository.getCommentById(id);
    }
}

package com.naumen.blogeng.controller;

import com.naumen.blogeng.model.BlogUser;
import com.naumen.blogeng.model.Comment;
import com.naumen.blogeng.model.Post;
import com.naumen.blogeng.repository.CommentRepository;
import com.naumen.blogeng.repository.PostRepository;
import com.naumen.blogeng.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DefaultController {

    //for TEST
    private UserRepository userRepository;
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    @Autowired
    public DefaultController(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository){
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }
    //for TEST


    @GetMapping("/")
    public String init(){
        //for TEST
        BlogUser blogUser = new BlogUser("213", "123", "123");
        Post post = new Post("123", "213");
        Comment comment = new Comment("text", post, blogUser);
        blogUser.addPost(post);
        post.addComment(comment);
        userRepository.save(blogUser);
        postRepository.save(post);
        commentRepository.save(comment);
        //for TEST
        return "index";
    }
}

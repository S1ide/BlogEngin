package com.naumen.blogeng.controller;

import com.naumen.blogeng.model.BlogUser;
import com.naumen.blogeng.service.CommentService;
import com.naumen.blogeng.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class DefaultController {

    private PostService postService;
    private CommentService commentService;

    @Autowired
    public DefaultController(PostService postService, CommentService commentService){
        this.postService = postService;
        this.commentService = commentService;
    }

    //for TEST
//    private UserRepository userRepository;
//    private PostRepository postRepository;
//    private CommentRepository commentRepository;
//
//    @Autowired
//    public DefaultController(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository){
//        this.userRepository = userRepository;
//        this.postRepository = postRepository;
//        this.commentRepository = commentRepository;
//    }
    //for TEST


    @GetMapping("/")
    public String init(){
        //for TEST
//        BlogUser blogUser = new BlogUser("213", "123", "123");
//        Post post = new Post("123", "213");
//        Comment comment = new Comment("text", post, blogUser);
//        blogUser.addPost(post);
//        post.addComment(comment);
//        userRepository.save(blogUser);
//        postRepository.save(post);
//        commentRepository.save(comment);
        //for TEST
        return "index";
    }

    //TODO получить юзера из контекста
    @PostMapping("/post")
    public String addPost(@RequestParam String header, @RequestParam String text){
        BlogUser blogUser = new BlogUser("123", "123", "123"); // временно пока не получили юзера из контекста
        postService.addPost(header, text, blogUser);
        return "redirect:/";
    }

    //TODO получить юзера из контекста
    @PostMapping("/post/{postId}/comment")
    public String addComment(@RequestParam String text, @PathVariable String postId){
        BlogUser blogUser = new BlogUser("123", "123", "123"); // временно пока не получили юзера из контекста
        commentService.addComment(text, postId, blogUser);
        return "redirect:/post/{postId}"; //отправляет на пока не существующую страницу, там должен будет быть пост с которого отправляли комментарий
    }
}

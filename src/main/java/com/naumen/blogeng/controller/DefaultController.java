package com.naumen.blogeng.controller;

import com.naumen.blogeng.repository.UserRepository;
import com.naumen.blogeng.service.CommentService;
import com.naumen.blogeng.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class DefaultController {

    private PostService postService;
    private CommentService commentService;
    private UserRepository userRepository;

    @Autowired
    public DefaultController(PostService postService, CommentService commentService, UserRepository userRepository){
        this.postService = postService;
        this.commentService = commentService;
        this.userRepository = userRepository;
    }
    @GetMapping("/")
    public String init(){
        return "index";
    }

    //TODO получить юзера из контекста
    @PostMapping("/post")
    public String addPost(@RequestParam String header, @RequestParam String text){
//        BlogUser blogUser = new BlogUser("123", "123", "123"); // временно пока не получили юзера из контекста
//        userRepository.save(blogUser);
        postService.addPost(header, text, blogUser);
        return "redirect:/";
    }

    //TODO получить юзера из контекста
    @PostMapping("/post/{postId}/comment")
    public String addComment(@RequestParam String text, @PathVariable String postId){
//        BlogUser blogUser = new BlogUser("123", "123", "123"); // временно пока не получили юзера из контекста
//        userRepository.save(blogUser); // для теста
        commentService.addComment(text, postId, blogUser);
        return "redirect:/post/{postId}"; //отправляет на пока не существующую страницу, там должен будет быть пост с которого отправляли комментарий
    }
}

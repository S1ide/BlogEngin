package com.naumen.blogeng.controller;

import com.naumen.blogeng.model.Post;
import com.naumen.blogeng.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;




@Controller
public class DefaultController {

    private PostService postService;

    @Autowired
    public DefaultController(PostService postService){
        this.postService = postService;
    }

    @GetMapping("/")
    public String blogPage(Model model){
        Iterable<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "index";
    }
}

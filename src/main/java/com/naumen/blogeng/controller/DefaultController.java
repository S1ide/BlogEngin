package com.naumen.blogeng.controller;

import com.naumen.blogeng.dto.DtoJoke;
import com.naumen.blogeng.model.Post;
import com.naumen.blogeng.service.JokeService;
import com.naumen.blogeng.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Controller
public class DefaultController {

    private PostService postService;
    private JokeService jokeService;


    @Autowired
    public DefaultController(PostService postService, JokeService jokeService){
        this.postService = postService;
        this.jokeService = jokeService;
    }

    @GetMapping("/")
    public String blogPage(Model model){
        List<Post> posts = new ArrayList<>(postService.getAllPosts());
        Collections.reverse(posts);
        DtoJoke joke = jokeService.getJoke();
        model.addAttribute("posts", posts);
        model.addAttribute("joke", joke);
        return "index";
    }

}

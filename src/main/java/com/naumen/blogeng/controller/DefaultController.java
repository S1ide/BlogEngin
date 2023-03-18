package com.naumen.blogeng.controller;

import com.naumen.blogeng.model.BlogUser;
import com.naumen.blogeng.model.Post;
import com.naumen.blogeng.repository.UserRepository;
import com.naumen.blogeng.service.CommentService;
import com.naumen.blogeng.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String blogPage(Model model){
        Iterable<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "index";
    }

    //TODO получить юзера из контекста,
    //Добавлен Model model
    @PostMapping("/post")
    public String addPost(@RequestParam String header, @RequestParam String text, Model model){
        BlogUser blogUser = new BlogUser("123", "123", "123"); // временно пока не получили юзера из контекста
        userRepository.save(blogUser);
        postService.addPost(header, text, blogUser);
        return "redirect:/";
    }

    //TODO получить юзера из контекста


    @PostMapping("/post/{postId}/comment")
    public String addComment(@RequestParam String text, @PathVariable String postId, Model model){
        BlogUser blogUser = new BlogUser("1234", "1234", "1234"); // временно пока не получили юзера из контекста
        userRepository.save(blogUser); // для теста
        commentService.addComment(text, postId, blogUser);

        return "redirect:/post/{postId}"; //отправляет на пока не существующую страницу, там должен будет быть пост с которого отправляли комментарий
    }

   // TODO добавить проверку id существует ли в бд
    @GetMapping("/post/{postId}")
    public String viewText(@PathVariable String postId, Model model){
    Post onePost = postService.getById(Long.parseLong(postId));
    model.addAttribute("onePost", onePost);
    model.addAttribute("comments", onePost.getComments());
    return "fullText";
    }

}

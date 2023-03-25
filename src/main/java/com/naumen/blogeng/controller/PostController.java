package com.naumen.blogeng.controller;

import com.naumen.blogeng.model.BlogUser;
import com.naumen.blogeng.model.Comment;
import com.naumen.blogeng.model.Post;
import com.naumen.blogeng.repository.UserRepository;
import com.naumen.blogeng.service.CommentService;
import com.naumen.blogeng.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/post")
public class PostController {


    private final PostService postService;
    private final CommentService commentService;
    private final UserRepository userRepository;

    @Autowired
    public PostController(PostService postService, CommentService commentService, UserRepository userRepository) {
        this.postService = postService;
        this.commentService = commentService;
        this.userRepository = userRepository;
    }


    //TODO получить юзера из контекста,
    //Добавлен Model model
    @PostMapping
    public String addPost(@RequestParam String header, @RequestParam String text, Model model) {
        BlogUser blogUser = new BlogUser("123", "123", "123"); // временно пока не получили юзера из контекста
        userRepository.save(blogUser);
        postService.addPost(header, text, blogUser);
        return "redirect:/";
    }

    //TODO получить юзера из контекста


    @PostMapping("/{postId}/comment")
    public String addComment(@RequestParam String text, @PathVariable String postId, Model model) {
        BlogUser blogUser = new BlogUser("1234", "1234", "1234"); // временно пока не получили юзера из контекста
        userRepository.save(blogUser); // для теста
        commentService.addComment(text, postId, blogUser);

        return "redirect:/post/{postId}"; //отправляет на пока не существующую страницу, там должен будет быть пост с которого отправляли комментарий
    }

    // TODO добавить проверку id существует ли в бд
    @GetMapping("/{postId}")
    public String viewText(@PathVariable String postId, Model model) {
        try {
            Post onePost = postService.getById(Long.parseLong(postId));
            model.addAttribute("onePost", onePost);
            model.addAttribute("comments", onePost.getComments());
            return "fullText";
        } catch (NullPointerException nullPointerException) {
            return "redirect:/";
        }

    }

    @GetMapping("/{postId}/edit")
    public String editPost(@PathVariable String postId, Model model) {
        try {
            Post onePost = postService.getById(Long.parseLong(postId));
            model.addAttribute("onePost", onePost);
            return "editPost";
        } catch (NullPointerException nullPointerException) {
            return "redirect:/";
        }
    }

    // структура верна?
    @PostMapping("/{postId}/edit")
    public String editPost(@PathVariable String postId, @RequestParam String header, @RequestParam String text, Model model) {
        Post post = postService.getById(Long.parseLong(postId));
        post.setHeader(header);
        post.setText(text);
        postService.updatePost(post);
        return "redirect:/post/{postId}";
    }

    // тот же вопр про структуру
    @PostMapping("/{postId}/remove")
    public String removePost(@PathVariable String postId, Model model) {
        Post post = postService.getById(Long.parseLong(postId));
        postService.removePost(post);
        return "redirect:/";
    }
}



package com.naumen.blogeng.controller;

import com.naumen.blogeng.model.BlogUser;
import com.naumen.blogeng.model.Comment;
import com.naumen.blogeng.model.Post;
import com.naumen.blogeng.repository.BlogUserRepository;
import com.naumen.blogeng.service.BlogUserService;
import com.naumen.blogeng.service.CommentService;
import com.naumen.blogeng.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/post")
public class PostController {


    private final PostService postService;
    private final CommentService commentService;
    private final BlogUserService blogUserService;

    @Autowired
    public PostController(PostService postService, CommentService commentService, BlogUserService blogUserService) {
        this.postService = postService;
        this.commentService = commentService;
        this.blogUserService = blogUserService;
    }


    //TODO получить юзера из контекста,
    //Добавлен Model model
    @PostMapping
    public String addPost(@RequestParam String header, @RequestParam String text, Model model) {
        postService.addPost(header, text, blogUserService.findUserByEmail(getCurrentUserEmail()));
        return "redirect:/";
    }

    //TODO получить юзера из контекста


    @PostMapping("/{postId}/comment")
    public String addComment(@RequestParam String text, @PathVariable String postId, Model model) {
        commentService.addComment(text, postId, blogUserService.findUserByEmail(getCurrentUserEmail()));
        return "redirect:/post/{postId}";
    }

    // TODO добавить проверку id существует ли в бд
    @GetMapping("/{postId}")
    public String viewText(@PathVariable String postId, Model model) {
        try {
            Post onePost = postService.getById(Long.parseLong(postId));
            BlogUser currentUser = blogUserService.findUserByEmail(getCurrentUserEmail());
            model.addAttribute("onePost", onePost);
            model.addAttribute("comments", onePost.getComments());
            model.addAttribute("currentUser", currentUser);
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


    @PostMapping("/{postId}/edit")
    public String editPost(@PathVariable String postId, @RequestParam String header, @RequestParam String text, Model model) {
        Post post = postService.getById(Long.parseLong(postId));
        post.setHeader(header);
        post.setText(text);
        postService.updatePost(post);
        return "redirect:/post/{postId}";
    }


    @PostMapping("/{postId}/remove")
    public String removePost(@PathVariable String postId, Model model) {
        Post post = postService.getById(Long.parseLong(postId));
        postService.removePost(post);
        return "redirect:/";
    }

    @GetMapping("/{postId}/comment/edit/{commentId}")
    public String editComment(@PathVariable String postId, @PathVariable String commentId, Model model) {
        try {
            Comment oneComment = commentService.getById(Long.parseLong(commentId));
            model.addAttribute("oneComment", oneComment);
            return "editComment";
        } catch (NullPointerException nullPointerException) {
            return "redirect:/";
        }
    }

    @PostMapping("/{postId}/comment/edit/{commentId}")
    public String editComment(@PathVariable String postId, @PathVariable String commentId, @RequestParam String textComment, Model model) {
        Comment oneComment = commentService.getById(Long.parseLong(commentId));
        oneComment.setTextComment(textComment);
        commentService.updateComment(oneComment);
        return "redirect:/";
    }

    @PostMapping("/{postId}/comment/remove/{commentId}")
    public String removeComment(@PathVariable String postId, @PathVariable String commentId, Model model) {
        Comment oneComment = commentService.getById(Long.parseLong(commentId));
        commentService.removeComment(oneComment);
        return "redirect:/";
    }

        private static String getCurrentUserEmail () {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return authentication.getName();
        }
    }




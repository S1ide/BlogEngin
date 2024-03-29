package com.naumen.blogeng.controller;

import com.naumen.blogeng.model.User;
import com.naumen.blogeng.model.Comment;
import com.naumen.blogeng.model.Post;
import com.naumen.blogeng.service.UserService;
import com.naumen.blogeng.service.CommentService;
import com.naumen.blogeng.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {


    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;


    @Autowired
    public PostController(PostService postService, CommentService commentService, UserService userService) {
        this.postService = postService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping
    public String addPost(@RequestParam String header, @RequestParam String text, @RequestParam(name = "image", required = false) MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            postService.addPostWithFile(header, text, userService.findUserByEmail(getCurrentUserEmail()), file);
        } else {
            postService.addPost(header, text, userService.findUserByEmail(getCurrentUserEmail()));
        }
        return "redirect:/";
    }

    @PostMapping("/{postId}/comment")
    public String addComment(@RequestParam String text, @PathVariable String postId) {
        commentService.addComment(text, postId, userService.findUserByEmail(getCurrentUserEmail()));
        return "redirect:/post/{postId}";
    }

    @GetMapping("/{postId}")
    public String viewText(@PathVariable String postId, Model model) {
        try {
            Post post = postService.getById(Long.parseLong(postId));
            User currentUser = userService.findUserByEmail(getCurrentUserEmail());
            List<Comment> comments = new ArrayList<>(post.getComments());
            Collections.reverse(comments);
            model.addAttribute("post", post);
            model.addAttribute("comments", comments);
            model.addAttribute("user", currentUser);
            return "post";
        } catch (NullPointerException nullPointerException) {
            return "redirect:/";
        }
    }

    @GetMapping("/{postId}/edit")
    public String editPost(@PathVariable String postId, Model model) {
        Post post = postService.getById(Long.parseLong(postId));
        model.addAttribute("post", post);
        return "editPost";
    }


    @PostMapping("/{postId}/edit")
    public String editPost(@PathVariable String postId, @RequestParam String header, @RequestParam String text) {
        Post post = postService.getById(Long.parseLong(postId));
        User currentUser = userService.findUserByEmail(getCurrentUserEmail());
        if (currentUser.getId() == post.getUser().getId() || isAdmin()) {
            post.setHeader(header);
            post.setText(text);
            postService.updatePost(post);
            return "redirect:/post/{postId}";
        }
        return "redirect:/post/{postId}";
    }


    @PostMapping("/{postId}/remove")
    public String removePost(@PathVariable String postId) {
        Post post = postService.getById(Long.parseLong(postId));
        User currentUser = userService.findUserByEmail(getCurrentUserEmail());
        if (currentUser.getId() == post.getUser().getId() || isAdmin()) {
            postService.removePost(post);
            return "redirect:/";
        }
        return "redirect:/";
    }

    @GetMapping("/{postId}/comment/edit/{commentId}")
    public String editComment(@PathVariable String postId, @PathVariable String commentId, Model model) {
        try {
            Comment comment = commentService.getById(Long.parseLong(commentId));
            model.addAttribute("comment", comment);
            return "editComment";
        } catch (NullPointerException nullPointerException) {
            return "redirect:/post/" + postId;
        }
    }

    @PostMapping("/{postId}/comment/edit/{commentId}")
    public String editComment(@PathVariable String postId, @PathVariable String commentId, @RequestParam String
            textComment) {
        Comment comment = commentService.getById(Long.parseLong(commentId));
        User currentUser = userService.findUserByEmail(getCurrentUserEmail());
        if (currentUser.getId() == comment.getUser().getId() || isAdmin()) {
            comment.setTextComment(textComment);
            commentService.updateComment(comment);
            return "redirect:/post/" + postId;
        }
        return "redirect:/post/" + postId;
    }

    @PostMapping("/{postId}/comment/remove/{commentId}")
    public String removeComment(@PathVariable String postId, @PathVariable String commentId) {
        Comment comment = commentService.getById(Long.parseLong(commentId));
        User currentUser = userService.findUserByEmail(getCurrentUserEmail());
        if (currentUser.getId() == comment.getUser().getId() || isAdmin()) {
            commentService.removeComment(comment);
        }
        return "redirect:/post/" + postId;
    }

    static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private static boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}




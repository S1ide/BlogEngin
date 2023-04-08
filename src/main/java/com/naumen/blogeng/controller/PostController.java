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
    public String addPost(@RequestParam String header, @RequestParam String text, Model model) {
        Post post = new Post(header, text, userService.findUserByEmail(getCurrentUserEmail()));
        postService.addPost(post);
        return "redirect:/";
    }

    @PostMapping("/{postId}/comment")
    public String addComment(@RequestParam String text, @PathVariable String postId, Model model) {
        commentService.addComment(text, postId, userService.findUserByEmail(getCurrentUserEmail()));
        return "redirect:/post/{postId}";
    }

    // TODO добавить проверку id существует ли в бд
    @GetMapping("/{postId}")
    public String viewText(@PathVariable String postId, Model model) {
        try {
            Post onePost = postService.getById(Long.parseLong(postId));
            User currentUser = userService.findUserByEmail(getCurrentUserEmail());
            model.addAttribute("onePost", onePost);
            model.addAttribute("comments", onePost.getComments());
            model.addAttribute("currentUser", currentUser);
            return "post";
        } catch (NullPointerException nullPointerException) {
            return "redirect:/";
        }

    }

    @GetMapping("/{postId}/edit")
    public String editPost(@PathVariable String postId, Model model) {
        Post onePost = postService.getById(Long.parseLong(postId));
        model.addAttribute("onePost", onePost);
        return "editPost";
    }


    @PostMapping("/{postId}/edit")
    public String editPost(@PathVariable String postId, @RequestParam String header, @RequestParam String text, Model model) {
        Post post = postService.getById(Long.parseLong(postId));
        User currentUser = userService.findUserByEmail(getCurrentUserEmail());
        if (currentUser.getId() == post.getUser().getId() || isAdmin()) {
            post.setHeader(header);
            post.setText(text);
            postService.updatePost(post);
        }
        return "redirect:/post/{postId}";
    }

    @PostMapping("/{postId}/remove")
    public String removePost(@PathVariable String postId, Model model) {
        Post post = postService.getById(Long.parseLong(postId));
        User currentUser = userService.findUserByEmail(getCurrentUserEmail());
        if (currentUser.getId() == post.getUser().getId() || isAdmin()) {
            postService.removePost(post);
        }
        return "redirect:/";
    }

    @GetMapping("/{postId}/comment/edit/{commentId}")
    public String editComment(@PathVariable String postId, @PathVariable String commentId, Model model) {
        try {
            Comment oneComment = commentService.getById(Long.parseLong(commentId));
            model.addAttribute("oneComment", oneComment);
            return "editComment";
        } catch (NullPointerException nullPointerException) {
            return "redirect:/post/" + postId;
        }
    }

    @PostMapping("/{postId}/comment/edit/{commentId}")
    public String editComment(@PathVariable String postId, @PathVariable String commentId, @RequestParam String
            textComment, Model model) {
        Comment oneComment = commentService.getById(Long.parseLong(commentId));
        User currentUser = userService.findUserByEmail(getCurrentUserEmail());
        if (currentUser.getId() == oneComment.getUser().getId() || isAdmin()) {
            oneComment.setTextComment(textComment);
            commentService.updateComment(oneComment);
            return "redirect:/post/" + postId;
        }
        return "redirect:/post/" + postId;
    }

    @PostMapping("/{postId}/comment/remove/{commentId}")
    public String removeComment(@PathVariable String postId, @PathVariable String commentId, Model model) {
        Comment oneComment = commentService.getById(Long.parseLong(commentId));
        User currentUser = userService.findUserByEmail(getCurrentUserEmail());
        if (currentUser.getId() == oneComment.getUser().getId() || isAdmin()) {
            commentService.removeComment(oneComment);
        }
        return "redirect:/post/" + postId;
    }

    private static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private static boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}




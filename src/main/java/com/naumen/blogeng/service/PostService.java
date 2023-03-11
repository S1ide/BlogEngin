package com.naumen.blogeng.service;


import com.naumen.blogeng.model.BlogUser;
import com.naumen.blogeng.model.Post;
import com.naumen.blogeng.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void addPost(String header, String text, BlogUser blogUser) {
        Post post = new Post(header, text, blogUser);
        postRepository.save(post);
    }
}

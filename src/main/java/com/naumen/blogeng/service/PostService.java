package com.naumen.blogeng.service;


import com.naumen.blogeng.model.BlogUser;
import com.naumen.blogeng.model.Post;
import com.naumen.blogeng.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Post getById(long id) {
        return postRepository.getPostById(id);
    }
    public List <Post> getAllPosts() {
        return postRepository.findAll();
    }

}

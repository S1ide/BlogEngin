package com.naumen.blogeng.service;


import com.naumen.blogeng.model.BlogUser;
import com.naumen.blogeng.model.Comment;
import com.naumen.blogeng.model.Post;
import com.naumen.blogeng.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void addPost(Post post) {
        postRepository.save(post);
    }
    public Post getById(long id) {
        return postRepository.getPostById(id);
    }
    public List <Post> getAllPosts() {
        return postRepository.findAll();
    }


    public void updatePost(Post post) {
        postRepository.save(post);
    }
    public void removePost(Post post) {
        postRepository.delete(post);
    }
    }



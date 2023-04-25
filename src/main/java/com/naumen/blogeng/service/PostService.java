package com.naumen.blogeng.service;


import com.naumen.blogeng.model.Post;
import com.naumen.blogeng.model.User;
import com.naumen.blogeng.repository.PostRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void addPost(String header, String text, User user) {
        Post post = new Post(header, text, user);
        postRepository.save(post);
    }

    public Post getById(long id) {
        return postRepository.getPostById(id);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public void updatePost(Post post) {
        postRepository.save(post);
    }

    public void removePost(Post post) {
        postRepository.delete(post);
    }
}



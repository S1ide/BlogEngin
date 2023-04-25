package com.naumen.blogeng.service;


import com.naumen.blogeng.model.Post;
import com.naumen.blogeng.model.User;
import com.naumen.blogeng.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + File.separator + "src" + File.separator +
            "main" + File.separator + "resources" + File.separator + "static";

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void addPost(String header, String text, User user) {
        Post post = new Post(header, text, user);
        postRepository.save(post);
    }
    public void addPostWithFile(String header, String text, User user, MultipartFile file) throws IOException {
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        String path = "/resources/static/" + file.getOriginalFilename();
        Post post = new Post(header, text, user, path);
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



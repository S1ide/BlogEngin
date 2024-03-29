package com.naumen.blogeng.service;


import com.naumen.blogeng.model.Image;
import com.naumen.blogeng.model.Post;
import com.naumen.blogeng.model.User;
import com.naumen.blogeng.repository.ImageRepository;
import com.naumen.blogeng.repository.PostRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    @Value( "${upload.path}" )
    private String directory;


    public PostService(PostRepository postRepository, ImageRepository imageRepository) {
        this.postRepository = postRepository;
        this.imageRepository = imageRepository;
    }

    public void addPost(String header, String text, User user) {
        Post post = new Post(header, text, user);
        postRepository.save(post);
    }

    public void addPostWithFile(String header, String text, User user, MultipartFile file) throws IOException {
        String[] strings = file.getOriginalFilename().split("\\.");
        String ext = strings[strings.length - 1];
        Image image = new Image(ImageIO.read(file.getInputStream()), ext);
        Post post = new Post(header, text, user, image);
        Path fileNameAndPath = Paths.get(directory, File.separator, imageRepository.count() + "." + image.getExt());
        Files.write(fileNameAndPath, file.getBytes());
        imageRepository.save(image);
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



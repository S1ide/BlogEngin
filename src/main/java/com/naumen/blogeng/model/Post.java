package com.naumen.blogeng.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.lang.NonNull;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotEmpty(message = "Header cannot be empty.")
    private String header;
    @NotEmpty(message = "Text cannot be empty.")
    @Column(columnDefinition="TEXT")
    private String text;
    private Date date;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image imagePath;

    @ManyToOne // Добавила еще зависимость с пользователем
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    protected Post() {};

    public Post(@NonNull String header, @NonNull String text, @NonNull User user) {
        this.header = header;
        this.text = text;
        this.user = user;
        this.date = new Date();
    }
    public Post(@NonNull String header, @NonNull String text, @NonNull User user, Image imagePath) {
        this.header = header;
        this.text = text;
        this.user = user;
        this.date = new Date();
        this.imagePath = imagePath;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setHeader(@NonNull String header) {
        this.header = header;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }

    @NonNull
    public String getHeader() {
        return header;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public long getId() {
        return id;
    }

    public Image getImagePath() {
        return imagePath;
    }

    public Image getPath() {
        return imagePath;
    }

}

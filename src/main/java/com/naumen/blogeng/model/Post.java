package com.naumen.blogeng.model;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    private String header;
    @NonNull
    private String text;
    private Date date;

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
}

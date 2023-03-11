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
    @JoinColumn(name = "blog_user_id")
    private BlogUser blogUser;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    protected Post() {
    }

    ;

    public Post(@NonNull String header, @NonNull String text, @NonNull BlogUser blogUser) {
        this.header = header;
        this.text = text;
        this.blogUser = blogUser;
        this.date = new Date();
    }

    public void setHeader(@NonNull String header) {
        this.header = header;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
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

    public BlogUser getBlogUser() {
        return blogUser;
    }
}

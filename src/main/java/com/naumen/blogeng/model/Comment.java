package com.naumen.blogeng.model;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private String textComment;
    private Date date;
    @ManyToOne
    @JoinColumn (name = "post_id")
    private Post post;
    @ManyToOne // Добавила еще зависимость с пользователем
    @JoinColumn (name = "blog_user_id")
    private User user;

    public Comment (@NonNull String textComment, Post post, User user) {
        this.textComment = textComment;
        this.date = new Date();
        this.post = post;
        this.user = user;
    }

    public Comment() {}

    public Long getId() { return id;}

    public Date getDate() {return date;}

    @NonNull
    public String getTextComment() {return textComment;}

    public User getUser() {
        return user;
    }

    public void setTextComment(@NonNull String textComment) {
        this.textComment = textComment;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}

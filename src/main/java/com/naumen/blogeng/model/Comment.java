package com.naumen.blogeng.model;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Comment {

    private final DateFormat dateFormat = new SimpleDateFormat("s:m:h dd/MM/yyyy");

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
    private BlogUser blogUser;

    public Comment (@NonNull String textComment, Post post, BlogUser blogUser) {
        this.textComment = textComment;
        this.date = dateFormat.getCalendar().getTime();
        this.post = post;
        this.blogUser = blogUser;
    }

    public Comment() {}

    public Long getId() { return id;}

    public Date getDate() {return date;}

    @NonNull
    public String getTextComment() {return textComment;}

    public void setTextComment(@NonNull String textComment) {
        this.textComment = textComment;
    }
}

package com.naumen.blogeng.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.lang.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Post {
    private final DateFormat dateFormat = new SimpleDateFormat("s:m:h dd/MM/yyyy");

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    private String header;
    @NonNull
    private String text;

    private Date date;

    protected Post(){};

    public Post(@NonNull String header, @NonNull String text) {
        this.header = header;
        this.text = text;
        this.date = dateFormat.getCalendar().getTime();
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
}

package com.naumen.blogeng.model;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
@Table(name="blog_users")
@Entity
//TODO рефактор кода: убрать ненужные приставки в нейминге;
public class BlogUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    @Column(name = "username")
    private String username;
    @NonNull
    @Column()
    private String password;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "blog_user_id")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "blog_user_id")
    private List<Comment> comments = new ArrayList<>();
    @NonNull
    private String email;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "roles_users",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles;

    //protected BlogUser() {}

    public BlogUser() {}

    public BlogUser(@NonNull String username, @NonNull String password, @NonNull String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Post> getPosts() {
        return posts;
    }
    public void addPost(Post post){
        posts.add(post);
    }

    public String getEmail() {
        return email;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}

package com.naumen.blogeng.model;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
@Table(name="users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    @Column(name = "firstName")
    private String firstName;

    @NonNull
    @Column(name = "lastName")
    private String lastName;
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
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "roles_users",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles;

    public User() {}

    public User(@NonNull String firstName, @NonNull String lastName, @NonNull String password, @NonNull String email) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.password = password;
        this.email = email;
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
        return firstName + " " + lastName;
    }

    public String getPassword() {
        return password;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
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

package com.naumen.blogeng.model;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.List;
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<BlogUser> users;

    public Role() {}

    public long getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public List<BlogUser> getUsers() {
        return users;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setUsers(List<BlogUser> users) {
        this.users = users;
    }
}

package com.naumen.blogeng.service;

import com.naumen.blogeng.dto.DtoUser;
import com.naumen.blogeng.model.User;

import java.util.List;

public interface UserService {
    void saveUser(DtoUser userDto);
    User findUserByEmail(String email);
    List<DtoUser> findAllUsers();
    void remove(String email);

    void changeFields(User user, String name, String lastName, String email, String password);
}

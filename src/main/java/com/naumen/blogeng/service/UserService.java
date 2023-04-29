package com.naumen.blogeng.service;

import com.naumen.blogeng.dto.DtoUser;
import com.naumen.blogeng.model.User;
import jakarta.persistence.Entity;

import java.util.List;

public interface UserService {
    void saveUser(DtoUser userDto);
    User findUserByEmail(String email);
    List<DtoUser> findAllUsers();
    void remove(String email);


}

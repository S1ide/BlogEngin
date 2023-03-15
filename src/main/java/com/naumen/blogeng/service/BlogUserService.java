package com.naumen.blogeng.service;

import com.naumen.blogeng.dto.DtoBlogUser;
import com.naumen.blogeng.model.BlogUser;

import java.util.List;

public interface BlogUserService {
    void saveUser(DtoBlogUser userDto);
    BlogUser findUserByEmail(String email);
    List<DtoBlogUser> findAllUsers();
}

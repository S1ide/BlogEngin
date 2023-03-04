package com.naumen.blogeng.controller;

import com.naumen.blogeng.model.BlogUser;
import com.naumen.blogeng.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DefaultController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/")
    public String init(){
        userRepository.save(new BlogUser("newUser", "123456", "sadsad@yandex.ru"));
        return "index";
    }
}

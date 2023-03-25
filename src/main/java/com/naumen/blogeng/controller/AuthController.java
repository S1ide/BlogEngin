package com.naumen.blogeng.controller;

import com.naumen.blogeng.dto.DtoBlogUser;
import com.naumen.blogeng.model.BlogUser;
import com.naumen.blogeng.service.BlogUserService;
import com.naumen.blogeng.service.BlogUserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {
    private BlogUserService blogUserService;

    public AuthController(BlogUserService blogUserService) {
        this.blogUserService = blogUserService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model){
        DtoBlogUser user = new DtoBlogUser();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/registration/save")
    public String registration(@Valid @ModelAttribute("user") DtoBlogUser dtoBlogUser, BindingResult result, Model model){

        BlogUser user = blogUserService.findUserByEmail(dtoBlogUser.getEmail());

        if (user != null && user.getEmail() != null && !user.getEmail().isEmpty()){
            result.rejectValue("email", null, "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", dtoBlogUser);
            return "/registration";
        }

        blogUserService.saveUser(dtoBlogUser);
        return "redirect:/login";

    }

    @GetMapping("/users")
    public String users(Model model){
        List<DtoBlogUser> users = blogUserService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }


}

package com.naumen.blogeng.controller;


import com.naumen.blogeng.model.User;
import com.naumen.blogeng.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.naumen.blogeng.controller.PostController.*;

@Controller
public class ProfileController {
    private final UserService userService;

    public ProfileController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showUserProfile(Model model) {
        User currentUser = userService.findUserByEmail(getCurrentUserEmail());
        model.addAttribute("user", currentUser);
        return "profile";
    }

    @PostMapping ("/profile/edit")
    public String changeUserProfile(@RequestParam String firstName, @RequestParam String lastName,@RequestParam String email, @RequestParam String password) {
        User currentUser = userService.findUserByEmail(getCurrentUserEmail());
        userService.changeFields(currentUser, firstName, lastName, email, password);
        if (!email.isEmpty() || !password.isEmpty()){
            return "redirect:/logout";
        }
        else return "redirect:/profile";
    }
    @GetMapping ("/profile/edit")
    public String changeUserProfile() {
        return "editProfile";
    }

    @PostMapping("/profile/change-image")
    public String changeProfileImage(@RequestParam(name = "image")MultipartFile file) throws IOException {
        User user = userService.findUserByEmail(getCurrentUserEmail());
        userService.setImage(user, file);
        return "redirect:/profile";
    }

}

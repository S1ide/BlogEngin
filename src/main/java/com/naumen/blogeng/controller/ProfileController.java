package com.naumen.blogeng.controller;


import com.naumen.blogeng.model.User;
import com.naumen.blogeng.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.naumen.blogeng.controller.PostController.*;

@Controller
public class ProfileController {
    private final UserService userService;

    public ProfileController (UserService userService) {
        this.userService = userService;
    }
// пароль отображается неверный
    @GetMapping("/profile")
    public String showUserProfile(Model model) {
        User currentUser = userService.findUserByEmail(getCurrentUserEmail());
        model.addAttribute("user", currentUser);
        return "profile";
    }

    //TODO после смены почты и пароля выход из УЗ
    @PostMapping ("/profile/edit")
    public String changeUserProfile(@RequestParam String firstName, @RequestParam String lastName,@RequestParam String email, @RequestParam String password) {
        User currentUser = userService.findUserByEmail(getCurrentUserEmail());
        userService.changeFields(currentUser, firstName, lastName, email, password);
        return "redirect:/profile";
    }
    @GetMapping ("/profile/edit")
    public String changeUserProfile() {
        return "editProfile";
    }

}

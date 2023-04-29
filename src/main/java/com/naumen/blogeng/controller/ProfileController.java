package com.naumen.blogeng.controller;

import com.naumen.blogeng.dto.DtoUser;
import com.naumen.blogeng.model.User;
import com.naumen.blogeng.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.naumen.blogeng.controller.PostController.getCurrentUserEmail;

@Controller
public class ProfileController {
    private final UserService userService;

    public ProfileController (UserService userService) {
        this.userService = userService;
    }
// пароль отображается неверный
    @GetMapping("/profile")
    public String showUserProfile( Model model) {
        User currentUser = userService.findUserByEmail(getCurrentUserEmail());
        model.addAttribute("user", currentUser);
        return "profile";
    }

    @PostMapping ("/profile/edit")
    public String changeUserProfile(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String password, @RequestParam String email) {
        User currentUser = userService.findUserByEmail(getCurrentUserEmail());
        currentUser.setFirstName(firstName);
        currentUser.setFirstName(lastName);
        currentUser.setFirstName(email);
        currentUser.setFirstName(password);
        //  userService.updateUser(currentUser);

        return "redirect:/profile";
    }
    @GetMapping ("/profile/edit")
    public String changeUserProfile( Model model) {
        User currentUser = userService.findUserByEmail(getCurrentUserEmail());
        model.addAttribute("user", currentUser);
        return "editProfile";
    }

}

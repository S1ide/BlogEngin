package com.naumen.blogeng.controller;

import com.naumen.blogeng.dto.DtoUser;
import com.naumen.blogeng.model.User;
import com.naumen.blogeng.repository.UserRepository;
import com.naumen.blogeng.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthController {
        private final UserService userService;

        public AuthController(UserService userService) {
            this.userService = userService;
        }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        DtoUser user = new DtoUser();
        model.addAttribute("user", user);
        return "registration";
    }
    @PostMapping("/registration/save")
    public String registration(@Valid @ModelAttribute("user") DtoUser dtoUser, BindingResult result, Model model) {
        User user = userService.findUserByEmail(dtoUser.getEmail());
        if (user != null && user.getEmail() != null && !user.getEmail().isEmpty()) {
            result.rejectValue("email", null, "There is already an account registered with the same email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", dtoUser);
            return "/registration";
        }
        userService.saveUser(dtoUser);
        return "redirect:/login";
    }
    @GetMapping("/users")
    public String users(Model model) {
        List<DtoUser> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
    @DeleteMapping("/users")
    public String deleteUser(@RequestParam("email") String email){
        userService.remove(email);
        return "redirect:/users";
    }
}

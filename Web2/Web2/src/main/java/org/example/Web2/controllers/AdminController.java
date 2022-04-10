package org.example.Web2.controllers;

import org.example.Web2.domain.User;
import org.example.Web2.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/admin")
    public String admin(Model model) {
        Iterable<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        return "admin";
    }
}

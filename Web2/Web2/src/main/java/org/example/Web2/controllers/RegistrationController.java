package org.example.Web2.controllers;

import org.example.Web2.domain.Role;
import org.example.Web2.domain.User;
import org.example.Web2.repos.UserRepo;
import org.example.Web2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String firstname,
                             @RequestParam String lastname,
                             @RequestParam String patronymic,
                             @RequestParam String phoneNumber,
                             @RequestParam String email,
                             Model model) {
        Response response = userService.createUser(username, password, firstname, lastname, patronymic, phoneNumber, email);
        model.addAttribute("message", response.getMessage());
        return response.getTarget();
    }
}
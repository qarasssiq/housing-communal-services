package org.example.Web2.controllers;

import org.example.Web2.domain.Role;
import org.example.Web2.domain.User;
import org.example.Web2.repos.UserRepo;
import org.example.Web2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String password,
                          @RequestParam String username,
                          @RequestParam String firstname,
                          @RequestParam String lastname,
                          @RequestParam String patronymic,
                          @RequestParam String phoneNumber,
                          @RequestParam String email,
                          Map<String, Object> model) {
        User userFromDb = userRepo.findByUsername(username);

        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "registration";
        }
        User user = new User(username, password, "ADMIN", firstname, lastname, patronymic, phoneNumber, email);
        user.setActive(true);
        userRepo.save(user);
        userService.addUser(user);
        return "redirect:/login";
    }
}
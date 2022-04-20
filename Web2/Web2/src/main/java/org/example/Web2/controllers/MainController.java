package org.example.Web2.controllers;

import org.example.Web2.domain.Address;
import org.example.Web2.domain.User;
import org.example.Web2.repos.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private AddressRepo addressRepo;

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User user, Model model) {
        Iterable<Address> addresses = addressRepo.findByUserId(user);
        model.addAttribute("addresses", addresses);
        return "main";
    }
}

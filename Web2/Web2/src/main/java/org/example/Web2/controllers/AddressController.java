package org.example.Web2.controllers;

import org.example.Web2.domain.Address;
import org.example.Web2.domain.User;
import org.example.Web2.repos.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressRepo addressRepo;

    @GetMapping("/create")
    public String createAddress(){
        return "createAddress";
    }

    @PostMapping("/create")
    public String createAddress(@AuthenticationPrincipal User user,
                                @RequestParam String city,
                                @RequestParam String shortAddress){
        Address address = new Address(city, shortAddress, user);
        addressRepo.save(address);
        return "redirect:/main";
    }
}

package org.example.Web2.controllers;

import org.example.Web2.domain.Address;
import org.example.Web2.domain.Bill;
import org.example.Web2.domain.User;
import org.example.Web2.repos.AddressRepo;
import org.example.Web2.repos.BillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private BillRepo billRepo;

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

    @GetMapping("/{id}")
    String address(@PathVariable("id") Long addressId, Model model) {
        Address address = addressRepo.findAddressById(addressId);
        Iterable<Bill> bills = billRepo.findByAddressId(address);
        model.addAttribute("address", address);
        model.addAttribute("bills", bills);
        return "address";
    }

    @GetMapping("/payBill/{id}")
    String payBill(@PathVariable("id") Long billId, Model model) {
        Bill bill = billRepo.findBillById(billId);
        bill.setStatus("Оплачено");
        billRepo.save(bill);
        return "redirect:/address/" + bill.getAddressId().getId();
    }

    @GetMapping("/{id}/history")
    String history(@PathVariable("id") Long addressId, Model model) {
        Address address = addressRepo.findAddressById(addressId);
        Iterable<Bill> bills = billRepo.findByAddressId(address);
        model.addAttribute("address", address);
        model.addAttribute("bills", bills);
        return "history";
    }
}

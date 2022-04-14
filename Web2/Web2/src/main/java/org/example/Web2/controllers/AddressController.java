package org.example.Web2.controllers;

import org.example.Web2.domain.Address;
import org.example.Web2.domain.Bill;
import org.example.Web2.domain.Service;
import org.example.Web2.domain.User;
import org.example.Web2.repos.AddressRepo;
import org.example.Web2.repos.BillRepo;
import org.example.Web2.repos.ServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private ServiceRepo serviceRepo;

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
        Iterable<Service> services = serviceRepo.findByAddressId(address);
        model.addAttribute("address", address);
        model.addAttribute("services", services);
        return "address";
    }

    @GetMapping("/{id}/service/{serviceId}")
    String address(@PathVariable("id") Long addressId, @PathVariable("serviceId") Long serviceId, Model model) {
        Service service = serviceRepo.findServiceById(serviceId);
        List<Bill> bills = billRepo.findByServiceIdAndIsPaidFalse(service);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        model.addAttribute("formatter", formatter);
        model.addAttribute("service", service);
        model.addAttribute("bills", bills);
        return "service";
    }

    @GetMapping("{addressId}/service/{serviceId}/payBill/{id}")
    String payBill(@PathVariable("id") Long id, Model model, @PathVariable String addressId, @PathVariable String serviceId) {
        Bill bill = billRepo.findBillById(id);
        bill.setIsPaid(true);
        billRepo.save(bill);
        return "redirect:/address/" + addressId + "/service/" + serviceId;
    }

    @GetMapping("{addressId}/createService")
    public String createService(@PathVariable String addressId){
        return "createService";
    }

    @PostMapping("{addressId}/createService")
    public String createService(@PathVariable Long addressId,
                                HttpServletRequest request,
                                Model model){
        Address address = addressRepo.findAddressById(addressId);
        List<Service> services = serviceRepo.findByAddressId(address);
        String error;
        for (Service service: services) {
            if(service.getType().equals(request.getParameter("type"))) {
                error = "Такая услуга уже есть.";
                model.addAttribute("error", error);
                return "createService";
            }
        }
        Service service = new Service(address, request.getParameter("type"), request.getParameter("personalAccountNumber"));
        serviceRepo.save(service);
        return "redirect:/address/" + addressId;
    }

    @GetMapping("{addressId}/service/{serviceId}/createBill/{type}")
    public String createBill(@PathVariable String addressId, @PathVariable String serviceId, @PathVariable String type){
        return "createBill";
    }

    @PostMapping("{addressId}/service/{serviceId}/createBill/{type}")
    public String createBill(@PathVariable Long addressId,
                             HttpServletRequest request,
                             @PathVariable Long serviceId,
                             @PathVariable String type,
                             Model model){
        Service service = serviceRepo.findServiceById(serviceId);
        double meter = Double.parseDouble(request.getParameter("meter"));
        double amount = switch (service.getType()) {
            case ("Вода") -> meter * 43.57;
            case ("Электричество") -> meter * 3.12;
            case ("Газ") -> meter * 7.73;
            default -> 0.0;
        };
        Bill bill = new Bill(service, meter, amount, LocalDate.now());
        billRepo.save(bill);
        return "redirect:/address/" + addressId + "/service/" + serviceId;
    }

//    @GetMapping("/{id}/history")
//    String history(@PathVariable("id") Long addressId, Model model) {
//        Address address = addressRepo.findAddressById(addressId);
//        Iterable<Bill> bills = billRepo.findByAddressId(address);
//        model.addAttribute("address", address);
//        model.addAttribute("bills", bills);
//        return "history";
//    }
}

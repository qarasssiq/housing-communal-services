package org.example.Web2.controllers;

import org.example.Web2.domain.*;
import org.example.Web2.repos.AddressRepo;
import org.example.Web2.repos.BillRepo;
import org.example.Web2.repos.ServiceRepo;
import org.example.Web2.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private ServiceRepo serviceRepo;

    @Autowired
    private BillRepo billRepo;

    @Autowired
    private AddressService addressService;

    @GetMapping("/create")
    public String createAddress(){
        return "createAddress";
    }

    @PostMapping("/create")
    public String createAddress(@AuthenticationPrincipal User user,
                                @RequestParam String city,
                                @RequestParam String shortAddress,
                                @RequestParam Integer numberOfResidents,
                                @RequestParam Integer apartmentSize){
        Address address = new Address(city, shortAddress, user, numberOfResidents, apartmentSize);
        addressRepo.save(address);
        return "redirect:/main";
    }

    @GetMapping("/{id}")
    String getAddress(@PathVariable("id") Long addressId, Model model) {
        Address address = addressRepo.findAddressById(addressId);
        Iterable<Service> services = serviceRepo.findByAddressId(address);
        model.addAttribute("address", address);
        model.addAttribute("services", services);
        return "address";
    }

    @GetMapping("/{id}/service/{serviceId}")
    String getService(@PathVariable("serviceId") Long serviceId, Model model, @PathVariable String id) {
        Service service = serviceRepo.findServiceById(serviceId);
        List<Bill> bills = billRepo.findByServiceIdAndIsPaidFalse(service);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        model.addAttribute("formatter", formatter);
        model.addAttribute("service", service);
        model.addAttribute("bills", bills);
        model.addAttribute("address", service.getAddressId());
        return "service";
    }

    @GetMapping("{addressId}/service/{serviceId}/confirmPayment/{id}/sum={amount}")
    String confirmPayment(@PathVariable("id") Long id,
                          @PathVariable Long addressId,
                          @PathVariable Long serviceId,
                          @PathVariable String amount) {
        addressService.confirmPayment(id, amount, serviceId);
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
        Response response = addressService.createService(addressId, request);
        model.addAttribute("error", response.getMessage());
        return response.getTarget();
    }

    @GetMapping("{addressId}/service/{serviceId}/createBill/{type}")
    public String createBill(@PathVariable Long addressId, @PathVariable Long serviceId, @PathVariable String type, Model model){
        Address address = addressRepo.findAddressById(addressId);

        double amount = switch (type) {
            case ("Капремонт") -> address.getApartmentSize() * 7.27;
            case ("Отопление") -> address.getApartmentSize() * 28.7;
            case ("Вывоз мусора") -> address.getNumberOfResidents() * 67.59;
            default -> 0.0;
        };

        model.addAttribute("service" , serviceRepo.findServiceById(serviceId));
        model.addAttribute("address", address);
        model.addAttribute("amount", amount);

        return "createBill";
    }

    @PostMapping("{addressId}/service/{serviceId}/createBill/{type}")
    public String createBill(@PathVariable Long addressId,
                             HttpServletRequest request,
                             @PathVariable Long serviceId,
                             @PathVariable String type,
                             Model model){
        Response response = addressService.createBill(addressId, serviceId, request);
        Address address = addressRepo.findAddressById(addressId);

        double amount = switch (type) {
            case ("Капремонт") -> address.getApartmentSize() * 7.27;
            case ("Отопление") -> address.getApartmentSize() * 28.7;
            case ("Вывоз мусора") -> address.getNumberOfResidents() * 67.59;
            default -> 0.0;
        };

        model.addAttribute("service" , serviceRepo.findServiceById(serviceId));
        model.addAttribute("error", response.getMessage());
        model.addAttribute("address", address);
        model.addAttribute("amount", amount);

        return response.getTarget();
    }

    @GetMapping("{addressId}/service/{serviceId}/history")
    String history(@PathVariable("addressId") Long addressId, Model model, @PathVariable Long serviceId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        model.addAttribute("formatter", formatter);
        model.addAttribute("payments", addressService.getPayments(serviceId));
        return "history";
    }
}

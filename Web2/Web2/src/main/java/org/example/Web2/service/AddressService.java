package org.example.Web2.service;

import org.example.Web2.controllers.Response;
import org.example.Web2.domain.Address;
import org.example.Web2.domain.Bill;
import org.example.Web2.domain.Payment;
import org.example.Web2.repos.AddressRepo;
import org.example.Web2.repos.BillRepo;
import org.example.Web2.repos.PaymentRepo;
import org.example.Web2.repos.ServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class AddressService {
    @Autowired
    AddressRepo addressRepo;

    @Autowired
    ServiceRepo serviceRepo;

    @Autowired
    BillRepo billRepo;

    @Autowired
    PaymentRepo paymentRepo;

    public void createBill(Long serviceId, HttpServletRequest request) {
        org.example.Web2.domain.Service service = serviceRepo.findServiceById(serviceId);
        double meter = Double.parseDouble(request.getParameter("meter"));
        double amount = switch (service.getType()) {
            case ("Вода") -> meter * 43.57;
            case ("Электричество") -> meter * 3.12;
            case ("Газ") -> meter * 7.73;
            default -> 0.0;
        };
        Bill bill = new Bill(service, meter, amount, LocalDate.now());
        billRepo.save(bill);
    }

    public Response createService(Long addressId, HttpServletRequest request) {
        Address address = addressRepo.findAddressById(addressId);
        Response response = new Response();
        String type = request.getParameter("type");
        if(serviceRepo.existsByAddressIdAndType(address, type)) {
            response.setMessage("Такая услуга уже есть!");
            response.setTarget("createService");
            return response;
        }
        org.example.Web2.domain.Service service = new org.example.Web2.domain.Service(address, type, request.getParameter("personalAccountNumber"));
        serviceRepo.save(service);
        response.setTarget("redirect:/address/" + addressId);

        return response;
    }

    public void confirmPayment(Long billId, String amount) {
        double parsedAmount = Double.parseDouble(amount);

        Bill bill = billRepo.findBillById(billId);

        double newAmount = bill.getAmount() - parsedAmount;
        bill.setAmount(newAmount);

        if (newAmount == 0.0) {
            bill.setIsPaid(true);
        }

        billRepo.save(bill);

        Payment payment = new Payment(bill, LocalDate.now(), parsedAmount, "checkAddress");
        paymentRepo.save(payment);
    }

    public Iterable<Payment> getPayments(Long serviceId) {
        org.example.Web2.domain.Service service = serviceRepo.findServiceById(serviceId);
        return paymentRepo.findPaymentsByBillId_ServiceId(service);
    }
}

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public Response createBill(Long addressId, Long serviceId, HttpServletRequest request) {
        Response response = new Response();

        Address address = addressRepo.findAddressById(addressId);

        org.example.Web2.domain.Service service = serviceRepo.findServiceById(serviceId);

        double meter = 0.0;

        if(service.getType().equals("Вода") || service.getType().equals("Электричество") || service.getType().equals("Газ")) {
            meter = Double.parseDouble(request.getParameter("meter"));

            if (service.getLastMeter() >= meter) {
                response.setMessage("Введённые показания должны быть больше предыдущих!");
                response.setTarget("createBill");
                return response;
            }
        } else {
            List<Bill> bills = billRepo.findByServiceId(service);
            for (Bill bill : bills) {
                LocalDate billDate = bill.getDate();
                if(billDate.getYear() == LocalDate.now().getYear() && billDate.getMonth() == LocalDate.now().getMonth()) {
                    response.setMessage("Счёт за этот месяц уже добавлен!");
                    response.setTarget("createBill");
                    return response;
                }
            }
        }

        double meterDifference = meter - service.getLastMeter();

        double amount = switch (service.getType()) {
            case ("Вода") -> meterDifference * 43.57;
            case ("Электричество") -> meterDifference * 3.12;
            case ("Газ") -> meterDifference * 7.73;
            case ("Капремонт") -> address.getApartmentSize() * 7.27;
            case ("Отопление") -> address.getApartmentSize() * 28.7;
            case ("Вывоз мусора") -> address.getNumberOfResidents() * 67.59;
            default -> 0.0;
        };
        Bill bill = new Bill(service, meter, amount, LocalDate.now());
        billRepo.save(bill);

        service.setLastMeter(meter);
        serviceRepo.save(service);

        response.setTarget("redirect:/address/" + addressId + "/service/" + serviceId);

        return response;
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

    public void confirmPayment(Long billId, String amount, Long serviceId) {
        double parsedAmount = Double.parseDouble(amount);

        Bill bill = billRepo.findBillById(billId);

        Payment payment = new Payment(bill, LocalDate.now(), parsedAmount, "checkAddress");
        paymentRepo.save(payment);

        org.example.Web2.domain.Service service = serviceRepo.findServiceById(serviceId);

        List<Bill> allBills = billRepo.findByServiceIdOrderByDateAsc(service);
        List<Bill> bills = new ArrayList<>();

        for (Bill allBill : allBills) {
            if (allBill.getDate().isBefore(bill.getDate()) || allBill.getDate().isEqual(bill.getDate())) {
                bills.add(allBill);
            }
        }

        for (Bill value : bills) {
            if(parsedAmount <= value.getAmount() - value.getPaidAmount()) {
                value.setPaidAmount(value.getPaidAmount() + parsedAmount);

                if (Objects.equals(value.getPaidAmount(), value.getAmount())) {
                    value.setIsPaid(true);
                }

                parsedAmount = 0.0;
            } else {
                parsedAmount -= value.getAmount() - value.getPaidAmount();
                value.setPaidAmount(value.getAmount());
                value.setIsPaid(true);
            }

            billRepo.save(value);

            if (parsedAmount == 0.0) {
                break;
            }
        }
    }

    public Iterable<Payment> getPayments(Long serviceId) {
        org.example.Web2.domain.Service service = serviceRepo.findServiceById(serviceId);
        return paymentRepo.findPaymentsByBillId_ServiceId(service);
    }
}

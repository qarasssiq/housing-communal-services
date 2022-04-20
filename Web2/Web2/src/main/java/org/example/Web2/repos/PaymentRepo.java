package org.example.Web2.repos;

import org.example.Web2.domain.Bill;
import org.example.Web2.domain.Payment;
import org.example.Web2.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
    List<Payment> findPaymentsByBillId_ServiceId(Service service);
}

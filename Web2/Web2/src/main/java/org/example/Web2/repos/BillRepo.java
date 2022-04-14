package org.example.Web2.repos;

import org.example.Web2.domain.Bill;
import org.example.Web2.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepo extends JpaRepository<Bill, Long> {
    List<Bill> findByServiceId(Service service);

    List<Bill> findByServiceIdAndIsPaidFalse(Service service);

    Bill findBillById(Long id);
}

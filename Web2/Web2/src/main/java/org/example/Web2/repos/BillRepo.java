package org.example.Web2.repos;


import org.example.Web2.domain.Address;
import org.example.Web2.domain.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepo extends JpaRepository<Bill, Long> {
    List<Bill> findByAddressId(Address address);

    Bill findBillById(Long id);
}

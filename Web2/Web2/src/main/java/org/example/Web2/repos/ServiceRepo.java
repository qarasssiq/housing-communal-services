package org.example.Web2.repos;

import org.example.Web2.domain.Address;
import org.example.Web2.domain.Bill;
import org.example.Web2.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepo extends JpaRepository<Service, Long> {
    Service findServiceById(Long id);

    List<Service> findByAddressId(Address address);
}

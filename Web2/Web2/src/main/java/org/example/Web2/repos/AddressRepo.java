package org.example.Web2.repos;

import org.example.Web2.domain.Address;
import org.example.Web2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepo extends JpaRepository<Address, Long> {
    Address findAddressById(Long id);

    List<Address> findByUserId(User user);
}

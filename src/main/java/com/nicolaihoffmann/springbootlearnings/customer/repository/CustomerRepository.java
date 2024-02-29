package com.nicolaihoffmann.springbootlearnings.customer.repository;

import com.nicolaihoffmann.springbootlearnings.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByExternalId(UUID id);
    @Transactional
    void deleteByExternalId(UUID externalId);
}
